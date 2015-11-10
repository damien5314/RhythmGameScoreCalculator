package com.ddiehl.rgsc

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ddiehl.rgsc.itg.ITGView

public class RGSCPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    object RGSCPagerAdapter {
        const val NUM_CALCULATORS = 1 // Temporarily until we implement DDR
    }

    private val mContext: Context = ContextProvider.get()

    override fun getCount(): Int {
        return RGSCPagerAdapter.NUM_CALCULATORS
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ITGView()
            1 -> return null
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
