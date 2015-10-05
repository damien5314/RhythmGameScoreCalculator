package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.BaseCalc

public class ITGCalc : BaseCalc() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ITGPresenter(context, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.calculator_inthegroove, null)
        mPresenter.loadSavedInput()
        return v
    }
}
