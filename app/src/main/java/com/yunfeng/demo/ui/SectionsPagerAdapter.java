package com.yunfeng.demo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<Fragment>(4);

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        Fragment fragment1 = new DummySectionFragment();
        mFragments.add(fragment1);
        Fragment fragment2 = new EmptyFragment();
        mFragments.add(fragment2);
        Fragment fragment3 = new EmptyFragment();
        mFragments.add(fragment3);
        Fragment fragment4 = new EmptyFragment();
        mFragments.add(fragment4);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "title_section0";
            case 1:
                return "title_section1";
            case 2:
                return "title_section2";
            case 3:
                return "title_section3";
        }
        return "";
    }
}
