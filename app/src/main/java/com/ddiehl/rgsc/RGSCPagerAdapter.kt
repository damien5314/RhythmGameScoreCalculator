package com.ddiehl.rgsc

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ddiehl.rgsc.ddrextreme.DDRExCalc
import com.ddiehl.rgsc.itg.ITGCalc

const val NUM_CALCULATORS = 2

public class RGSCPagerAdapter(c: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mContext: Context = c

    override fun getCount(): Int {
        return NUM_CALCULATORS
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ITGCalc()
            1 -> return DDRExCalc()
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return mContext.getString(R.string.tab_itg)
            1 -> return mContext.getString(R.string.tab_ddrex)
            else -> return null
        }
    }
}
