package com.ddiehl.rgsc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.ddiehl.rgsc.calculators.CalculatorInTheGroove;

public class ViewPagerActivity extends ActionBarActivity {
    public static final String TAG = ViewPagerActivity.class.getSimpleName();

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new CalculatorInTheGroove();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }
}
