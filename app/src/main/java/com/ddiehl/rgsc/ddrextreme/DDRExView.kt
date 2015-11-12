package com.ddiehl.rgsc.ddrextreme

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Switch
import butterknife.bindView
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.ScoreViewFragment
import com.ddiehl.rgsc.data.Score

class DDRExView : ScoreViewFragment() {
    private val _marvellouses: EditText by bindView(R.id.ddrex_marvellouses)
    private val _perfects: EditText by bindView(R.id.ddrex_perfects)
    private val _greats: EditText by bindView(R.id.ddrex_greats)
    private val _goods: EditText by bindView(R.id.ddrex_goods)
    private val _boos: EditText by bindView(R.id.ddrex_boos)
    private val _misses: EditText by bindView(R.id.ddrex_misses)
    private val _holds: EditText by bindView(R.id.ddrex_holds)
    private val _totalHolds: EditText by bindView(R.id.ddrex_total_holds)
    private val _marvellousSwitch: Switch by bindView(R.id.ddrex_marvellous_switch)

    var marvellouses: Int = 0; get() = getInputFrom(_marvellouses)
    var perfects: Int = 0; get() = getInputFrom(_perfects)
    var greats: Int = 0; get() = getInputFrom(_greats)
    var goods: Int = 0; get() = getInputFrom(_goods)
    var boos: Int = 0; get() = getInputFrom(_boos)
    var misses: Int = 0; get() = getInputFrom(_misses)
    var holds: Int = 0; get() = getInputFrom(_holds)
    var totalHolds: Int = 0; get() = getInputFrom(_totalHolds)
    var marvellousesEnabled: Boolean
        get() = _marvellousSwitch.isChecked
        set(enabled) { _marvellousSwitch.isChecked = enabled }

    override val calculatorLayoutResId: Int = R.layout.calculator_ddrex

    override fun getPresenter(): ScorePresenter {
        return DDRExPresenter(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _marvellousSwitch.setOnCheckedChangeListener({ compoundButton, checked ->
            marvellousesEnabled = checked
            _marvellouses.isFocusable = checked
            _marvellouses.isFocusableInTouchMode = checked
            if (checked) {
                // Enable marvelous field
                _marvellouses.inputType = InputType.TYPE_CLASS_NUMBER
                // Set next focus target for total holds field
                _totalHolds.nextFocusDownId = _marvellouses.id
                _totalHolds.nextFocusForwardId = _marvellouses.id
            } else {
                // Disable marvelous field and clear
                _marvellouses.setText("")
                _marvellouses.inputType = InputType.TYPE_NULL
                // Set next focus target for total holds field
                _totalHolds.nextFocusDownId = _perfects.id
                _totalHolds.nextFocusForwardId = _perfects.id
            }
            _presenter.onScoreUpdated()
        })
        // Set initial state for switch
        _marvellouses.setText("")
        _marvellouses.inputType = InputType.TYPE_NULL
        _marvellousSwitch.isChecked = false
    }

    override fun displayInput(score: Score) {
        _marvellouses.setText(stripZero(score.elements[DDRExScore.MARVELLOUSES]?.count))
        _perfects.setText(stripZero(score.elements[DDRExScore.PERFECTS]?.count))
        _greats.setText(stripZero(score.elements[DDRExScore.GREATS]?.count))
        _goods.setText(stripZero(score.elements[DDRExScore.GOODS]?.count))
        _boos.setText(stripZero(score.elements[DDRExScore.BOOS]?.count))
        _misses.setText(stripZero(score.elements[DDRExScore.MISSES]?.count))
        _holds.setText(stripZero(score.elements[DDRExScore.HOLDS]?.count))
        _totalHolds.setText(stripZero(score.elements[DDRExScore.TOTAL_HOLDS]?.count))
    }

    fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    override fun clearErrors() {
        _holds.error = null
    }
}