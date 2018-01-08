package it.oztaking.com.slidepicad;

import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPaper;
    private ArrayList<ImageView> imageViewList;
    private ImageView imageView;
    private LinearLayout ll_point_container;
    private TextView tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        初始化布局View视图
        initView();
//        Model数据
        initData();
//        Controller控制器
        initAdapter();
    }



    private void initView() {
        viewPaper = (ViewPager) findViewById(R.id.viewPaper);

        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }


    private void initData() {

        //初始化图片资源数组
        int[] ImageResIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        imageViewList = new ArrayList<>();
        for (int i=0; i < ImageResIds.length; i++){
            imageView = new ImageView(this);
            imageView.setBackgroundResource(ImageResIds[i]);
            imageViewList.add(imageView);
        }
    }



    private void initAdapter() {

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
    }
}
