package it.oztaking.com.slidepicad;

import android.app.Activity;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPaper;
    private ArrayList<ImageView> imageViewList;

    private LinearLayout ll_point_container;
    private TextView tv_desc;


    private String[] contentDescs;
    private int previousSelectedPosition = 0;
    private int[] imageResIds;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

//        初始化布局View视图
        initView();
//        Model数据
        initData();
//        Controller控制器
        initAdapter();

        //图片的自动滚动
        autoSlidingPic();
    }

    private void autoSlidingPic() {
        new Thread(){
            public void run(){
            isRunning = true;
            while (isRunning){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPaper.setCurrentItem(viewPaper.getCurrentItem()+1);
                    }
                });

            }
        }}.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initView() {
        viewPaper = (ViewPager) findViewById(R.id.viewPaper);
        viewPaper.setOnPageChangeListener(this);
        //设置可以左右缓冲的图片的个数
//        viewPaper.setOffscreenPageLimit(1);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
        //找到textView
        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }


    private void initData() {
        //初始化图片资源数组
        imageResIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
//文本描述
        contentDescs = new String[]{
                "巩俐不低俗，我就不能低俗",
                "朴树又来了！再唱经典老歌引万人大合唱...",
                "揭秘北京电影如何升级",
                "乐视TV版大派送",
                "热血吊丝的反杀"
        };

        imageViewList = new ArrayList<ImageView>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++){
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            //加小白点 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(10,10);
            //修改指示器的距离
            if (i != 0){
                layoutParams.leftMargin = 10;
            }
            //变为灰色
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);

        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        System.out.println("onPageSelected:"+position);
        //数值需要重新定义否则越界
        int newPosition = position % imageViewList.size();
        System.out.println("ImageViewList.size() 资源的大小"+imageViewList.size());
        //内容随着图片的滚动而滚动
        tv_desc.setText(contentDescs[newPosition]);
//        for (int i=0; i < ll_point_container.getChildCount(); i++){
//            View childAt = ll_point_container.getChildAt(newPosition);
//            childAt.setEnabled(newPosition == i);
//        }
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        Toast.makeText(getApplicationContext(),"当前选中条目"+newPosition,Toast.LENGTH_SHORT).show();
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initAdapter() {
        //将0号为置为true
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;
        //设置适配器
        viewPaper.setAdapter(new MyAdapter());
        viewPaper.setCurrentItem(5000000);

    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        //指定复用的判断逻辑 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }


        //1.返回要显示的条目的内容，创建条目

        /**
         *
         * @param container 容器 viewPaper
         * @param position  当前要显示条目的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化："+position);

            //数值需要重新定义否则越界
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
//          a.  将View对象添加到container中
            container.addView(imageView);
//          b. 将view对象返回给框架 适配器
            return imageView;
//            return super.instantiateItem(container, position);
        }

        //2.销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destoryItem 销毁："+ position);
            container.removeView((View) object);

//            super.destroyItem(container, position, object);
        }

    }

}
