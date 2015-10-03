package com.ddiehl.rgsc

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import butterknife.bindView

public class MainActivity : AppCompatActivity() {
    object MainActivity {
        val TAG_ITG = "ITG"
        val TAG_DDREX = "DDR_EXTREME"
    }

    private val tabs: TabLayout by bindView(R.id.tabs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabs.addTab(tabs.newTab().setText("ITG").setTag(MainActivity.TAG_ITG));
        tabs.addTab(tabs.newTab().setText("DDR Ex").setTag(MainActivity.TAG_DDREX));
    }
}