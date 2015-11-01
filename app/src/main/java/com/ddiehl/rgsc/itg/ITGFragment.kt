package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import butterknife.bindView
import com.ddiehl.rgsc.BaseCalc
import com.ddiehl.rgsc.R
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.android.widget.OnTextChangeEvent
import rx.android.widget.WidgetObservable
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

public class ITGFragment : BaseCalc(), ITGView {
    private lateinit var presenter: ITGPresenter

    // View bindings
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
    private val _earnedScoreValue: TextView by bindView(R.id.earned_score_value)
    private val _potentialScoreValue: TextView by bindView(R.id.potential_score_value)
    private val _scorePercent: TextView by bindView(R.id.score_percent)
    private val _scoreGrade: TextView by bindView(R.id.score_grade)
    private val _clearButton: View by bindView(R.id.clear_form)

    // Public properties
    override var fantastics: Int = 0; get() = readIntegerFrom(_fantastics)
    override var excellents: Int = 0; get() = readIntegerFrom(_excellents)
    override var greats: Int = 0; get() = readIntegerFrom(_greats)
    override var decents: Int = 0; get() = readIntegerFrom(_decents)
    override var wayoffs: Int = 0; get() = readIntegerFrom(_wayoffs)
    override var misses: Int = 0; get() = readIntegerFrom(_misses)
    override var holds: Int = 0; get() = readIntegerFrom(_holds)
    override var totalHolds: Int = 0; get() = readIntegerFrom(_totalHolds)
    override var mines: Int = 0; get() = readIntegerFrom(_mines)
    override var rolls: Int = 0; get() = readIntegerFrom(_rolls)
    override var totalRolls: Int = 0; get() = readIntegerFrom(_totalRolls)

    private fun readIntegerFrom(t: EditText): Int {
        val s = t.text.toString()
        return if (s == "") 0 else s.toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ITGPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_inthegroove, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _clearButton.setOnClickListener { presenter.onScoreClear() }
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun displayInput(score: ITGScore) {
        _fantastics.setText(stripZero(score.fantastics))
        _excellents.setText(stripZero(score.excellents))
        _greats.setText(stripZero(score.greats))
        _decents.setText(stripZero(score.decents))
        _wayoffs.setText(stripZero(score.wayoffs))
        _misses.setText(stripZero(score.misses))
        _holds.setText(stripZero(score.holds))
        _totalHolds.setText(stripZero(score.totalHolds))
        _mines.setText(stripZero(score.mines))
        _rolls.setText(stripZero(score.rolls))
        _totalRolls.setText(stripZero(score.totalRolls))
    }

    private fun stripZero(i: Int): String? {
        if (i == 0) return null;
        else return i.toString()
    }

    override fun showNoStepsError() {
        Snackbar.make(view, R.string.error_no_steps, Snackbar.LENGTH_SHORT).show()
    }

    override fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    override fun showRollsInvalid() {
        _rolls.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    override fun clearErrors() {
        _holds.error = null
        _rolls.error = null
    }

    override fun clearForm() {
        _fantastics.setText("")
        _excellents.setText("")
        _greats.setText("")
        _decents.setText("")
        _wayoffs.setText("")
        _misses.setText("")
        _holds.setText("")
        _totalHolds.setText("")
        _mines.setText("")
        _rolls.setText("")
        _totalRolls.setText("")
        clearErrors()
    }

    override fun showEarned(earned: Int) {
        _earnedScoreValue.text = earned.toString()
    }

    override fun showPotential(potential: Int) {
        _potentialScoreValue.text = potential.toString()
    }

    override fun showScorePercentage(scorePercent: Double) {
        val df = DecimalFormat("0.00")
        _scorePercent.text = df.format(scorePercent) + "%"
    }

    override fun showScoreGrade(gradeString: String) {
        _scoreGrade.text = gradeString
    }

    private fun showScoreError() {
        _earnedScoreValue.text = getString(R.string.earned_score_error)
        _potentialScoreValue.text = getString(R.string.potential_score_error)
        _scorePercent.text = ""
        _scoreGrade.text = ""
    }

    override fun getTextChangedObservable(): Observable<OnTextChangeEvent> {
        return Observable.merge(listOf(
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
        ))
    }

    private fun getTextChangedObservable(t: EditText): rx.Observable<OnTextChangeEvent> {
        return WidgetObservable.text(t)
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext { e: OnTextChangeEvent -> presenter.onScoreUpdated() }
    }
}
