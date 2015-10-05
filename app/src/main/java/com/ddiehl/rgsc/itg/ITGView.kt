package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import butterknife.bindView
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.BaseCalc

public class ITGView : BaseCalc() {
    private val _fantastics : EditText by bindView(R.id.fantastics)
    val fantastics : Int = _fantastics.text.toString().toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ITGPresenter(context, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.calculator_inthegroove, null)
        val score = mPresenter.loadSavedInput()
        // TODO: Populate score into view
        return v
    }

}
