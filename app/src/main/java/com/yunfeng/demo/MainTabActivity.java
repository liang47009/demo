package com.yunfeng.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yunfeng.demo.ui.EmptyFragment;
import com.yunfeng.demo.ui.widget.ShadeView;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private List<EmptyFragment> tabFragments;

    private List<ShadeView> tabIndicators;

    private ViewPager viewPager;

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        initData();
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initData() {
        tabFragments = new ArrayList<>();
        tabIndicators = new ArrayList<>();
        String[] titles = new String[]{"home", "center", "me"};
        for (String title : titles) {
            EmptyFragment tabFragment = new EmptyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Title", title);
            tabFragment.setArguments(bundle);
            tabFragments.add(tabFragment);
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return tabFragments.get(arg0);
            }
        };
        initTabIndicator();
    }

    private void initTabIndicator() {
        ShadeView one = (ShadeView) findViewById(R.id.sv_home);
        ShadeView two = (ShadeView) findViewById(R.id.sv_center);
        ShadeView three = (ShadeView) findViewById(R.id.sv_me);
        tabIndicators.add(one);
        tabIndicators.add(two);
        tabIndicators.add(three);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        one.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View v) {
        resetTabsStatus();

        switch (v.getId()) {
            case R.id.sv_home:
                tabIndicators.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.sv_center:
                tabIndicators.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.sv_me:
                tabIndicators.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
        }

    }

    private void resetTabsStatus() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            tabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("TAG", "position==" + position);
        Log.e("TAG", "positionOffset==" + positionOffset);
        Log.e("TAG", "positionOffsetPixels==" + positionOffsetPixels);
        if (positionOffset > 0) {
            ShadeView leftTab = tabIndicators.get(position);
            ShadeView rightTab = tabIndicators.get(position + 1);
            leftTab.setIconAlpha(1 - positionOffset);
            rightTab.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
