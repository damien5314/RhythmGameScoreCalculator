package com.ddiehl.rgsc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddiehl.rgsc.calculators.Calculator_InTheGroove;

import java.util.ArrayList;
import java.util.List;

public class ViewPageActivity extends FragmentActivity {
    public static final String TAG = ViewPageActivity.class.getSimpleName();

    ViewPager pager;
    ViewPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        List<Fragment> fragments = getFragments();
        pageAdapter = new ViewPageAdapter(getSupportFragmentManager(), fragments);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(Calculator_InTheGroove.newInstance());
        return fragmentList;
    }

    public static class ViewPageFragment extends Fragment {
        private final static String LAYOUT_ID = "id";

        public ViewPageFragment() { }

        public static Fragment newInstance(int id) {
            ViewPageFragment frag = new ViewPageFragment();
            Bundle args = new Bundle();
            args.putInt(LAYOUT_ID, id);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(getArguments().getInt(LAYOUT_ID), container, false);
        }
    }
}
