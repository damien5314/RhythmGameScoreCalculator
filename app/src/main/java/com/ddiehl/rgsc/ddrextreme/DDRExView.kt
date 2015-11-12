package com.ddiehl.rgsc.ddrextreme

import android.widget.EditText
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

    var marvellouses: Int = 0; get() = getInputFrom(_marvellouses)
    var perfects: Int = 0; get() = getInputFrom(_perfects)
    var greats: Int = 0; get() = getInputFrom(_greats)
    var goods: Int = 0; get() = getInputFrom(_goods)
    var boos: Int = 0; get() = getInputFrom(_boos)
    var misses: Int = 0; get() = getInputFrom(_misses)
    var holds: Int = 0; get() = getInputFrom(_holds)
    var totalHolds: Int = 0; get() = getInputFrom(_totalHolds)

    override val calculatorLayoutResId: Int = R.layout.calculator_ddrex

    override fun getPresenter(): ScorePresenter {
        return DDRExPresenter(this)
    }

    override fun getEmptyScore(): Score {
        return DDRExScore()
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