package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import butterknife.bindView
import com.ddiehl.rgsc.BaseCalc
import com.ddiehl.rgsc.R
import com.orhanobut.logger.Logger
import java.text.DecimalFormat

public class ITGView : BaseCalc() {
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
    private val _clearButton: ImageButton by bindView(R.id.clear_form)

    // Public properties
    var fantastics: Int = 0; get() = readIntegerFrom(_fantastics)
    var excellents: Int = 0; get() = readIntegerFrom(_excellents)
    var greats: Int = 0; get() = readIntegerFrom(_greats)
    var decents: Int = 0; get() = readIntegerFrom(_decents)
    var wayoffs: Int = 0; get() = readIntegerFrom(_wayoffs)
    var misses: Int = 0; get() = readIntegerFrom(_misses)
    var holds: Int = 0; get() = readIntegerFrom(_holds)
    var totalHolds: Int = 0; get() = readIntegerFrom(_totalHolds)
    var mines: Int = 0; get() = readIntegerFrom(_mines)
    var rolls: Int = 0; get() = readIntegerFrom(_rolls)
    var totalRolls: Int = 0; get() = readIntegerFrom(_totalRolls)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ITGPresenter(context, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_inthegroove, null)
        return view
    }

    private val tw: TextWatcher = object: TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {/* no-op */}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {/* no-op */}
        override fun afterTextChanged(s: Editable?) {
            presenter.onScoreUpdated()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _clearButton.setOnClickListener { clearForm() }
        subscribeToTextChangedEvents()
    }

    private fun subscribeToTextChangedEvents() {
        _fantastics.addTextChangedListener(tw)
        _excellents.addTextChangedListener(tw)
        _greats.addTextChangedListener(tw)
        _decents.addTextChangedListener(tw)
        _wayoffs.addTextChangedListener(tw)
        _misses.addTextChangedListener(tw)
        _holds.addTextChangedListener(tw)
        _totalHolds.addTextChangedListener(tw)
        _mines.addTextChangedListener(tw)
        _rolls.addTextChangedListener(tw)
        _totalRolls.addTextChangedListener(tw)
    }

    private fun unsubscribeToTextChangedEvents() {
        _fantastics.removeTextChangedListener(tw)
        _excellents.removeTextChangedListener(tw)
        _greats.removeTextChangedListener(tw)
        _decents.removeTextChangedListener(tw)
        _wayoffs.removeTextChangedListener(tw)
        _misses.removeTextChangedListener(tw)
        _holds.removeTextChangedListener(tw)
        _totalHolds.removeTextChangedListener(tw)
        _mines.removeTextChangedListener(tw)
        _rolls.removeTextChangedListener(tw)
        _totalRolls.removeTextChangedListener(tw)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    fun displayInput(s: ITGScore) {
        _fantastics.setText(s.fantastics.toString())
        _excellents.setText(s.excellents.toString())
        _greats.setText(s.greats.toString())
        _decents.setText(s.decents.toString())
        _wayoffs.setText(s.wayoffs.toString())
        _misses.setText(s.misses.toString())
        _holds.setText(s.holds.toString())
        _totalHolds.setText(s.totalHolds.toString())
        _mines.setText(s.mines.toString())
        _rolls.setText(s.rolls.toString())
        _totalRolls.setText(s.totalRolls.toString())
    }

    fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    fun showRollsInvalid() {
        _rolls.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    fun clearErrors() {
        _holds.error = null
        _rolls.error = null
        _earnedScoreValue.text = ""
        _potentialScoreValue.text = ""
        _scorePercent.text = ""
        _scoreGrade.text = ""
    }

    fun showNoStepsError() {
        Snackbar.make(view, R.string.error_no_steps, Snackbar.LENGTH_SHORT).show()
    }

    fun clearForm() {
        unsubscribeToTextChangedEvents()
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
        _earnedScoreValue.setText(R.string.score_value_earned_default)
        _potentialScoreValue.setText(R.string.score_value_potential_default)
        _scorePercent.setText(R.string.score_percent_default)
        _scoreGrade.setText(R.string.score_grade_default)
        subscribeToTextChangedEvents()
    }

    fun showEarned(earned: Int) {
        _earnedScoreValue.text = earned.toString()
    }

    fun showPotential(potential: Int) {
        _potentialScoreValue.text = potential.toString()
    }

    fun showScorePercentage(scorePercent: Double) {
        val df = DecimalFormat("0.00")
        _scorePercent.text = df.format(scorePercent) + "%"
    }

    fun showScoreGrade(gradeString: String) {
        _scoreGrade.text = gradeString
    }

    private fun readIntegerFrom(t: EditText): Int {
        val s = t.text.toString()
        return if (s == "") 0 else s.toInt()
    }

    private fun showScoreError() {
        _earnedScoreValue.text = getString(R.string.earned_score_error)
        _potentialScoreValue.text = getString(R.string.potential_score_error)
        _scorePercent.text = ""
        _scoreGrade.text = ""
    }
}
