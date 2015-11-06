package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.bindView
import com.ddiehl.rgsc.BaseCalc
import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.RGSC
import com.jakewharton.rxbinding.widget.RxTextView
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

public class ITGFragment : BaseCalc(), ITGView {
    private val logger = RGSC.getLogger()
    private lateinit var presenter: ITGPresenter

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
    private val _scoreValueArea: TextView by bindView(R.id.score_value_area)
    private val _scorePercent: TextView by bindView(R.id.score_percent)
    private val _scoreGrade: TextView by bindView(R.id.score_grade)

    private val _clearButton: Button by bindView(R.id.keypad_clear_score)
    private val _nextButton: Button by bindView(R.id.keypad_next)
    private val _keypad: ViewGroup by bindView(R.id.keypad_layout)
    private val _keypad_1: Button by bindView(R.id.keypad_1)
    private val _keypad_2: Button by bindView(R.id.keypad_2)
    private val _keypad_3: Button by bindView(R.id.keypad_3)
    private val _keypad_4: Button by bindView(R.id.keypad_4)
    private val _keypad_5: Button by bindView(R.id.keypad_5)
    private val _keypad_6: Button by bindView(R.id.keypad_6)
    private val _keypad_7: Button by bindView(R.id.keypad_7)
    private val _keypad_8: Button by bindView(R.id.keypad_8)
    private val _keypad_9: Button by bindView(R.id.keypad_9)
    private val _keypad_0: Button by bindView(R.id.keypad_0)

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

    private lateinit var _onTextChangedEvent: Observable<CharSequence>
    private var _onTextChangedEventSubscription: Subscription? = null

    private var currentFocusedField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ITGPresenter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_inthegroove, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _onTextChangedEvent = getTextChangedObservable()
        disableEditText()
        setOnFocusListeners()
        setKeypadClickListeners()
    }

    private fun readIntegerFrom(t: EditText): Int {
        val s = t.text.toString()
        return if (s == "") 0 else s.toInt()
    }

    private fun setKeypadClickListeners() {
        setDigitClickListener(_keypad_0)
        setDigitClickListener(_keypad_1)
        setDigitClickListener(_keypad_2)
        setDigitClickListener(_keypad_3)
        setDigitClickListener(_keypad_4)
        setDigitClickListener(_keypad_5)
        setDigitClickListener(_keypad_6)
        setDigitClickListener(_keypad_7)
        setDigitClickListener(_keypad_8)
        setDigitClickListener(_keypad_9)
        _clearButton.setOnClickListener { presenter.onScoreClear() }
        _nextButton.setOnClickListener {
            val nextFocusId = currentFocusedField?.nextFocusForwardId
            if (nextFocusId != null) activity.findViewById(nextFocusId)?.requestFocus()
        }
    }

    private fun setDigitClickListener(button: Button) {
        button.setOnClickListener {
            val text: String = currentFocusedField?.text.toString()
            currentFocusedField?.setText(text + "" + button.tag)
        }
    }

    private fun disableEditText() {
        disableEditText(_fantastics)
        disableEditText(_excellents)
        disableEditText(_greats)
        disableEditText(_decents)
        disableEditText(_wayoffs)
        disableEditText(_misses)
        disableEditText(_holds)
        disableEditText(_totalHolds)
        disableEditText(_mines)
        disableEditText(_rolls)
        disableEditText(_totalRolls)
    }

    private fun disableEditText(t: EditText) {
        t.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        t.setTextIsSelectable(true)
    }

    private val keypadNumberOnFocusChangeListener = View.OnFocusChangeListener {
        view: View, visible: Boolean ->
        _keypad.visibility = if (visible) View.VISIBLE else View.GONE
        currentFocusedField = if (visible) view as EditText else null
    }

    private fun setOnFocusListeners() {
        _fantastics.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _excellents.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _greats.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _decents.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _wayoffs.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _misses.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _holds.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _totalHolds.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _mines.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _rolls.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _totalRolls.onFocusChangeListener = keypadNumberOnFocusChangeListener
    }

    override fun onStart() {
        super.onStart()
        subscribeToTextChangedEvents()
        presenter.onStart()
    }

    override fun onStop() {
        unsubscribeFromTextChangedEvents()
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
        unsubscribeFromTextChangedEvents()
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
        subscribeToTextChangedEvents()
    }

    override fun showScoreValues(earned: Int, potential: Int) {
        val scoreValueFormatter = getString(R.string.score_value_formatter)
        _scoreValueArea.text = scoreValueFormatter.format(earned, potential, potential - earned)
    }

    override fun showScorePercentage(scorePercent: Double) {
        val df = DecimalFormat("0.00")
        _scorePercent.text = df.format(scorePercent) + "%"
    }

    override fun showScoreGrade(gradeString: String) {
        _scoreGrade.text = gradeString
    }

    private fun showScoreError() {
        val scoreValueFormatter = getString(R.string.score_value_formatter)
        _scoreValueArea.text = scoreValueFormatter.format(0, 0, 0)
        _scorePercent.text = ""
        _scoreGrade.text = ""
    }

    override fun getTextChangedObservable(): Observable<CharSequence> {
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
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }

    private fun getTextChangedObservable(t: EditText): rx.Observable<CharSequence> =
            RxTextView.textChanges(t)

    private fun subscribeToTextChangedEvents() {
        _onTextChangedEventSubscription = _onTextChangedEvent
                .skip(1) // Value is emitted immediately on subscribe
                .subscribe({ presenter.onScoreUpdated() }, { }, { })
    }

    private fun unsubscribeFromTextChangedEvents() {
        _onTextChangedEventSubscription?.unsubscribe()
    }
}
