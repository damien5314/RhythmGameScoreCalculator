package com.ddiehl.rgsc

import android.support.v4.app.Fragment

abstract class BaseCalc : Fragment(), CalculatorView {
    lateinit var mPresenter: CalculatorPresenter

    override fun displayScore() {

    }
}