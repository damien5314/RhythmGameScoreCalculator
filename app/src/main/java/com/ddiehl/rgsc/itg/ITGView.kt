package com.ddiehl.rgsc.itg

import android.widget.EditText
import butterknife.bindView
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.ScoreViewFragment
import com.ddiehl.rgsc.data.Score
import rx.Observable

class ITGView : ScoreViewFragment() {
    private val _fantastics: EditText by bindView(R.id.fantastics)
    private val _excellents: EditText by bindView(R.id.excellents)
    private val _greats: EditText by bindView(R.id.greats)
    private val _decents: EditText by bindView(R.id.decents)
    private val _wayoffs: EditText by bindView(R.id.wayoffs)
    private val _misses: EditText by bindView(R.id.misses)
    private val _holds: EditText by bindView(R.id.holds)
    private val _totalHolds: EditText by bindView(R.id.total_holds)
    private val _mines: EditText by bindView(R.id.mines)
    private val _rolls: EditText by bindView(R.id.rolls)
    private val _totalRolls: EditText by bindView(R.id.total_rolls)

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

    override fun getPresenter(): ScorePresenter {
        return ITGPresenter(this)
    }

    override fun getScoreEntryFields(): List<EditText> {
        return listOf(_fantastics, _excellents, _greats, _decents, _wayoffs,
            _misses, _holds, _totalHolds, _mines, _rolls, _totalRolls)
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

    override fun getTextChangedObservableList(): List<Observable<CharSequence>> {
        return listOf(
                getTextChangedObservable(_fantastics),
                getTextChangedObservable(_excellents),
                getTextChangedObservable(_greats),
                getTextChangedObservable(_decents),
                getTextChangedObservable(_wayoffs),
                getTextChangedObservable(_misses),
                getTextChangedObservable(_holds),
                getTextChangedObservable(_totalHolds),
                getTextChangedObservable(_mines),
                getTextChangedObservable(_rolls),
                getTextChangedObservable(_totalRolls)
        )
    }
}