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
    private val _marvelouses: EditText by bindView(R.id.ddrex_marvelouses)
    private val _perfects: EditText by bindView(R.id.ddrex_perfects)
    private val _greats: EditText by bindView(R.id.ddrex_greats)
    private val _goods: EditText by bindView(R.id.ddrex_goods)
    private val _boos: EditText by bindView(R.id.ddrex_boos)
    private val _misses: EditText by bindView(R.id.ddrex_misses)
    private val _holds: EditText by bindView(R.id.ddrex_holds)
    private val _totalHolds: EditText by bindView(R.id.ddrex_total_holds)
    private val _marvelousSwitch: Switch by bindView(R.id.ddrex_marvelous_switch)

    var marvelouses: Int = 0; get() = getInputFrom(_marvelouses)
    var perfects: Int = 0; get() = getInputFrom(_perfects)
    var greats: Int = 0; get() = getInputFrom(_greats)
    var goods: Int = 0; get() = getInputFrom(_goods)
    var boos: Int = 0; get() = getInputFrom(_boos)
    var misses: Int = 0; get() = getInputFrom(_misses)
    var holds: Int = 0; get() = getInputFrom(_holds)
    var totalHolds: Int = 0; get() = getInputFrom(_totalHolds)
    var marvelousEnabled: Boolean
        get() = _marvelousSwitch.isChecked
        set(enabled) { _marvelousSwitch.isChecked = enabled }

    override val calculatorLayoutResId: Int = R.layout.calculator_ddrex

    override fun getPresenter(): ScorePresenter {
        return DDRExPresenter(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _marvelousSwitch.setOnCheckedChangeListener({ compoundButton, checked ->
            marvelousEnabled = checked
            _marvelouses.isFocusable = checked
            _marvelouses.isFocusableInTouchMode = checked
            if (checked) {
                // Enable marvelous field
                _marvelouses.inputType = InputType.TYPE_CLASS_NUMBER
                // Set next focus target for total holds field
                _totalHolds.nextFocusDownId = _marvelouses.id
                _totalHolds.nextFocusForwardId = _marvelouses.id
            } else {
                // Disable marvelous field and clear
                _marvelouses.setText("")
                _marvelouses.inputType = InputType.TYPE_NULL
                // Set next focus target for total holds field
                _totalHolds.nextFocusDownId = _perfects.id
                _totalHolds.nextFocusForwardId = _perfects.id
            }
            _presenter.onScoreUpdated()
        })
        // Set initial state for switch
        _marvelouses.setText("")
        _marvelouses.inputType = InputType.TYPE_NULL
        _marvelousSwitch.isChecked = false
    }

    override fun displayInput(score: Score) {
        _marvelouses.setText(stripZero(score.elements[DDRExScore.MARVELOUSES]?.count))
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