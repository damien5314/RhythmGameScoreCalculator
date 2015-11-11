package com.ddiehl.rgsc

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.bindView

public class MainActivity : AppCompatActivity() {
    private val _tabs: TabLayout by bindView(R.id.tabs)
    private val _viewPager: ViewPager by bindView(R.id.view_pager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _viewPager.adapter = RGSCPagerAdapter(supportFragmentManager)
        _tabs.setupWithViewPager(_viewPager)
    }
}
