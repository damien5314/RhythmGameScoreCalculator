package com.ddiehl.rgsc.ddrsn2

import android.view.View
import android.widget.EditText
import butterknife.bindView
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.ScoreViewFragment
import com.ddiehl.rgsc.data.Score
import java.text.DecimalFormat
import java.text.NumberFormat

class DDRSN2View : ScoreViewFragment() {
    private val _marvelouses: EditText by bindView(R.id.ddrsn2_marvelouses)
    private val _perfects: EditText by bindView(R.id.ddrsn2_perfects)
    private val _greats: EditText by bindView(R.id.ddrsn2_greats)
    private val _goods: EditText by bindView(R.id.ddrsn2_goods)
    private val _boos: EditText by bindView(R.id.ddrsn2_boos)
    private val _misses: EditText by bindView(R.id.ddrsn2_misses)
    private val _holds: EditText by bindView(R.id.ddrsn2_holds)
    private val _totalHolds: EditText by bindView(R.id.ddrsn2_total_holds)

    var marvelouses: Int = 0; get() = getInputFrom(_marvelouses)
    var perfects: Int = 0; get() = getInputFrom(_perfects)
    var greats: Int = 0; get() = getInputFrom(_greats)
    var goods: Int = 0; get() = getInputFrom(_goods)
    var boos: Int = 0; get() = getInputFrom(_boos)
    var misses: Int = 0; get() = getInputFrom(_misses)
    var holds: Int = 0; get() = getInputFrom(_holds)
    var totalHolds: Int = 0; get() = getInputFrom(_totalHolds)

    override protected val calculatorLayoutResId: Int = R.layout.calculator_ddrsn2

    override fun getPresenter(): ScorePresenter {
        return DDRSN2Presenter(this)
    }

    override fun displayInput(score: Score) {
        _marvelouses.setText(stripZero(score.elements[DDRSN2Score.MARVELOUSES]?.count))
        _perfects.setText(stripZero(score.elements[DDRSN2Score.PERFECTS]?.count))
        _greats.setText(stripZero(score.elements[DDRSN2Score.GREATS]?.count))
        _goods.setText(stripZero(score.elements[DDRSN2Score.GOODS]?.count))
        _boos.setText(stripZero(score.elements[DDRSN2Score.BOOS]?.count))
        _misses.setText(stripZero(score.elements[DDRSN2Score.MISSES]?.count))
        _holds.setText(stripZero(score.elements[DDRSN2Score.HOLDS]?.count))
        _totalHolds.setText(stripZero(score.elements[DDRSN2Score.TOTAL_HOLDS]?.count))
    }

    override fun showScoreValues(earned: Int, potential: Int) {
        _scoreValueArea.text = NumberFormat.getInstance().format(earned)
    }

    override fun showScorePercentage(scorePercent: Double) {
        // No-op for DDR SN2
        _scorePercent.visibility = View.GONE
    }

    override fun showScoreError() {
        _scoreValueArea.text = "-"
        _scoreGrade.text = "-"
    }

    fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    override fun clearErrors() {
        _holds.error = null
    }
}
