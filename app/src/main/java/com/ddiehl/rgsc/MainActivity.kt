package com.ddiehl.rgsc

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.bindView

const val TAB_ITG = "ITG"
const val TAB_DDREX = "DDR_EXTREME"

public class MainActivity : AppCompatActivity() {
    private val mTabs: TabLayout by bindView(R.id.tabs)
    private val mViewPager: ViewPager by bindView(R.id.view_pager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewPager.adapter = RGSCPagerAdapter(this, supportFragmentManager)
        mTabs.setupWithViewPager(mViewPager)
    }
}
