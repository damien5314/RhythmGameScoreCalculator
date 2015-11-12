package com.ddiehl.rgsc.itg

import android.widget.EditText
import butterknife.bindView
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.ScoreViewFragment
import com.ddiehl.rgsc.data.Score

class ITGView : ScoreViewFragment() {
    private val _fantastics: EditText by bindView(R.id.itg_fantastics)
    private val _excellents: EditText by bindView(R.id.itg_excellents)
    private val _greats: EditText by bindView(R.id.itg_greats)
    private val _decents: EditText by bindView(R.id.itg_decents)
    private val _wayoffs: EditText by bindView(R.id.itg_wayoffs)
    private val _misses: EditText by bindView(R.id.itg_misses)
    private val _holds: EditText by bindView(R.id.itg_holds)
    private val _totalHolds: EditText by bindView(R.id.itg_total_holds)
    private val _mines: EditText by bindView(R.id.itg_mines)
    private val _rolls: EditText by bindView(R.id.itg_rolls)
    private val _totalRolls: EditText by bindView(R.id.itg_total_rolls)

    var fantastics: Int = 0; get() = getInputFrom(_fantastics)
    var excellents: Int = 0; get() = getInputFrom(_excellents)
    var greats: Int = 0; get() = getInputFrom(_greats)
    var decents: Int = 0; get() = getInputFrom(_decents)
    var wayoffs: Int = 0; get() = getInputFrom(_wayoffs)
    var misses: Int = 0; get() = getInputFrom(_misses)
    var holds: Int = 0; get() = getInputFrom(_holds)
    var totalHolds: Int = 0; get() = getInputFrom(_totalHolds)
    var mines: Int = 0; get() = getInputFrom(_mines)
    var rolls: Int = 0; get() = getInputFrom(_rolls)
    var totalRolls: Int = 0; get() = getInputFrom(_totalRolls)

    override protected val calculatorLayoutResId: Int = R.layout.calculator_itg

    override fun getPresenter(): ScorePresenter {
        return ITGPresenter(this)
    }

    override fun getEmptyScore(): Score {
        return ITGScore()
    }

    override fun addGameSpecificViews() {
        // no-op for ITG
    }

    override fun displayInput(score: Score) {
        _fantastics.setText(stripZero(score.elements[ITGScore.FANTASTICS]?.count))
        _excellents.setText(stripZero(score.elements[ITGScore.EXCELLENTS]?.count))
        _greats.setText(stripZero(score.elements[ITGScore.GREATS]?.count))
        _decents.setText(stripZero(score.elements[ITGScore.DECENTS]?.count))
        _wayoffs.setText(stripZero(score.elements[ITGScore.WAY_OFFS]?.count))
        _misses.setText(stripZero(score.elements[ITGScore.MISSES]?.count))
        _holds.setText(stripZero(score.elements[ITGScore.HOLDS]?.count))
        _totalHolds.setText(stripZero(score.elements[ITGScore.TOTAL_HOLDS]?.count))
        _mines.setText(stripZero(score.elements[ITGScore.MINES]?.count))
        _rolls.setText(stripZero(score.elements[ITGScore.ROLLS]?.count))
        _totalRolls.setText(stripZero(score.elements[ITGScore.TOTAL_ROLLS]?.count))
    }

    fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    fun showRollsInvalid() {
        _rolls.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    override fun clearErrors() {
        _holds.error = null
        _rolls.error = null
    }
}