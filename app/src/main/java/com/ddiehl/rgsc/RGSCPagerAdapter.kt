package com.ddiehl.rgsc

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ddiehl.rgsc.ddrextreme.DDRExView
import com.ddiehl.rgsc.ddrsn2.DDRSN2View
import com.ddiehl.rgsc.itg.ITGView

public class RGSCPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    object RGSCPagerAdapter {
        const val NUM_CALCULATORS = 3
    }

    private val _context: Context = ContextProvider.get()

    override fun getCount(): Int {
        return RGSCPagerAdapter.NUM_CALCULATORS
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return ITGView()
            1 -> return DDRExView()
            2 -> return DDRSN2View()
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return _context.getString(R.string.tab_itg)
            1 -> return _context.getString(R.string.tab_ddrex)
            2 -> return _context.getString(R.string.tab_ddrsn2)
            else -> return null
        }
    }
}
