package com.ddiehl.rgsc.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddiehl.rgsc.R

class DDRExCalc : BaseCalc() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = View.inflate(context, R.layout.calculator_ddrextreme, null)
        return v
    }
}
