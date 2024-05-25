package com.example.myapplication01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab1,tab2,tab3,tab4;
    private List<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化数据
        //对单选按钮进行监听，选中、未选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_attraction) {
                    mViewPager.setCurrentItem(0);
                } else if (i == R.id.rb_facility) {
                    mViewPager.setCurrentItem(1);
                } else if (i == R.id.rb_food) {
                    mViewPager.setCurrentItem(2);
                } else if (i == R.id.rb_radio) {
                    mViewPager.setCurrentItem(3);
                }
            }
        });
    }

    private void initView() {
        // 初始化控件
        mViewPager = findViewById(R.id.viewpager);
        mRadioGroup = findViewById(R.id.rg_tab);
        tab1 = findViewById(R.id.rb_attraction);
        tab2 = findViewById(R.id.rb_facility);
        tab3 = findViewById(R.id.rb_food);
        tab4 = findViewById(R.id.rb_radio);

        // 创建Fragment列表
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AttractionFragment());
        fragments.add(new FacilityFragment());
        fragments.add(new FoodFragment());
        fragments.add(new RadioFragment());

        // 设置适配器
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);

        // 对ViewPager监听，让分页和底部图标保持一起滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 此方法可以留空
            }

            @Override // 让ViewPager滑动的时候，下面的图标跟着变动
            public void onPageSelected(int position) {
                mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 此方法可以留空
            }
        });
    }

    // ViewPager适配器
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

//    MapView mMapView = null;
//    //初始化地图控制器对象
//    AMap aMap;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        //获取地图控件引用
//        mMapView = (MapView) findViewById(R.id.amapView);
//        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//        mMapView.onCreate(savedInstanceState);
//        if (aMap == null) {
//            aMap = mMapView.getMap();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mMapView.onDestroy();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        mMapView.onResume();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        mMapView.onPause();
//    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mMapView.onSaveInstanceState(outState);
//    }
}