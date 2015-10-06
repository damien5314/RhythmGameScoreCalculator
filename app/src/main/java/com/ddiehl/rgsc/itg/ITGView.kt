package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.ddiehl.rgsc.BaseCalc
import com.ddiehl.rgsc.R
import java.text.DecimalFormat

public class ITGView : BaseCalc() {
    private val presenter = ITGPresenter(context, this)

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
    val fantastics: Int = _fantastics.text.toString().toInt()
    val excellents: Int = _excellents.text.toString().toInt()
    val greats: Int = _greats.text.toString().toInt()
    val decents: Int = _decents.text.toString().toInt()
    val wayoffs: Int = _wayoffs.text.toString().toInt()
    val misses: Int = _misses.text.toString().toInt()
    val holds: Int = _holds.text.toString().toInt()
    val totalHolds: Int = _totalHolds.text.toString().toInt()
    val mines: Int = _mines.text.toString().toInt()
    val rolls: Int = _rolls.text.toString().toInt()
    val totalRolls: Int = _totalRolls.text.toString().toInt()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator_inthegroove, null)
        _clearButton.setOnClickListener { clearForm() }
        return view
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
        _greats.setText(s.excellents.toString())
        _decents.setText(s.excellents.toString())
        _wayoffs.setText(s.excellents.toString())
        _misses.setText(s.excellents.toString())
        _holds.setText(s.excellents.toString())
        _totalHolds.setText(s.excellents.toString())
        _mines.setText(s.excellents.toString())
        _rolls.setText(s.excellents.toString())
        _totalRolls.setText(s.excellents.toString())
    }

    fun showHoldsInvalid() {
        _holds.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    fun showRollsInvalid() {
        _rolls.error = getString(R.string.error_invalid_holds)
        showScoreError()
    }

    private fun showScoreError() {
        _earnedScoreValue.text = getString(R.string.earned_score_error)
        _potentialScoreValue.text = getString(R.string.potential_score_error)
        _scorePercent.text = ""
        _scoreGrade.text = ""
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
}
