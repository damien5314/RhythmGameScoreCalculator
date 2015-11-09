package com.ddiehl.rgsc.itg

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
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
    companion object {
        private val SCORE_CALC_DELAY = 500L
        private val CLEAR_ALL_TIME_THRESHOLD_MS = 1500L
    }

    private val _logger = RGSC.getLogger()
    private lateinit var _presenter: ITGPresenter
    private val _handler = Handler()

    private val _scoreEntryScrollView: ScrollView by bindView(R.id.score_entry_scrollview)
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
    private lateinit var _scoreEntryFields: List<EditText>

    private val _calculatedScoreArea: ViewGroup by bindView(R.id.calculated_score)
    private val _scoreValueArea: TextView by bindView(R.id.score_value_area)
    private val _scorePercent: TextView by bindView(R.id.score_percent)
    private val _scoreGrade: TextView by bindView(R.id.score_grade)

    private val _keypadLayout: ViewGroup by bindView(R.id.keypad_layout)
    private val _deleteButton: Button by bindView(R.id.keypad_delete)
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
    private lateinit var _keypadButtons: List<Button>

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

    private var _currentFocusedField: EditText? = null
    private var _deleteTapCounter = 0
    private val _deleteButtonRunnable = { _deleteTapCounter = 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = ITGPresenter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_inthegroove, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _scoreEntryFields = listOf(_fantastics, _excellents, _greats, _decents, _wayoffs,
                _misses, _holds, _totalHolds, _mines, _rolls, _totalRolls)
        _keypadButtons = listOf(_keypad_0, _keypad_1, _keypad_2, _keypad_3, _keypad_4,
                _keypad_5, _keypad_6, _keypad_7, _keypad_8, _keypad_9)
        _keypadLayout.visibility = View.GONE
        _onTextChangedEvent = getTextChangedObservable()
        disableEditText()
        setOnFocusListeners()
        setKeypadClickListeners()
        _calculatedScoreArea.setOnClickListener {
            _currentFocusedField?.clearFocus()
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeToTextChangedEvents()
        _presenter.onStart()
    }

    override fun onStop() {
        unsubscribeFromTextChangedEvents()
        _presenter.onStop()
        super.onStop()
    }

    private fun readIntegerFrom(t: EditText): Int {
        val s = t.text.toString()
        return if (s == "") 0 else s.toInt()
    }

    private fun setKeypadClickListeners() {
        for (button in _keypadButtons) setDigitClickListener(button)
        _deleteButton.setOnClickListener {
            _handler.removeCallbacks(_deleteButtonRunnable)
            _currentFocusedField?.setText("")
            _deleteTapCounter++
            if (_deleteTapCounter >= 3) {
                _deleteTapCounter = 0
                clearForm()
            } else {
                _handler.postDelayed(_deleteButtonRunnable, CLEAR_ALL_TIME_THRESHOLD_MS)
            }
        }
        _nextButton.setOnClickListener {
            val nextFocusId = _currentFocusedField?.nextFocusForwardId
            if (nextFocusId != null) activity.findViewById(nextFocusId)?.requestFocus()
        }
    }

    private fun setDigitClickListener(button: Button) {
        button.setOnClickListener {
            val text: String = _currentFocusedField?.text.toString()
            _currentFocusedField?.setText(text + "" + button.tag)
            _currentFocusedField?.setSelection(_currentFocusedField?.text?.length ?: 0);
        }
    }

    private fun disableEditText() {
        for (et in _scoreEntryFields) disableEditText(et)
    }

    private fun disableEditText(t: EditText) {
        t.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        t.setTextIsSelectable(true)
    }

    private fun showKeypadListener(view: View, visible: Boolean) {
        _keypad.visibility = if (visible) View.VISIBLE else View.GONE
        _currentFocusedField = if (visible) view as EditText else null
    }

    private val keypadNumberOnFocusChangeListener = View.OnFocusChangeListener {
        view, visible -> showKeypadListener(view, visible)
    }

    private val keypadNumberScrollUpOnFocusChangeListener = View.OnFocusChangeListener {
        view, visible ->
        showKeypadListener(view, visible)
        _scoreEntryScrollView.scrollTo(0, 0)
    }

    private fun setOnFocusListeners() {
        for (et in _scoreEntryFields) et.onFocusChangeListener = keypadNumberOnFocusChangeListener
        _fantastics.onFocusChangeListener = keypadNumberScrollUpOnFocusChangeListener
        _holds.onFocusChangeListener = keypadNumberScrollUpOnFocusChangeListener
    }

    override fun displayInput(score: ITGScore) {
        _fantastics.setText(stripZero(score.elements[ITGScore.FANTASTICS]!!.count))
        _excellents.setText(stripZero(score.elements[ITGScore.EXCELLENTS]!!.count))
        _greats.setText(stripZero(score.elements[ITGScore.GREATS]!!.count))
        _decents.setText(stripZero(score.elements[ITGScore.DECENTS]!!.count))
        _wayoffs.setText(stripZero(score.elements[ITGScore.WAY_OFFS]!!.count))
        _misses.setText(stripZero(score.elements[ITGScore.MISSES]!!.count))
        _holds.setText(stripZero(score.elements[ITGScore.HOLDS]!!.count))
        _totalHolds.setText(stripZero(score.elements[ITGScore.TOTAL_HOLDS]!!.count))
        _mines.setText(stripZero(score.elements[ITGScore.MINES]!!.count))
        _rolls.setText(stripZero(score.elements[ITGScore.ROLLS]!!.count))
        _totalRolls.setText(stripZero(score.elements[ITGScore.TOTAL_ROLLS]!!.count))
    }

    private fun stripZero(i: Int): String {
        if (i == 0) return ""
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
        for (et in _scoreEntryFields) et.setText("")
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
                .debounce(SCORE_CALC_DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }

    private fun getTextChangedObservable(t: EditText): rx.Observable<CharSequence> =
            RxTextView.textChanges(t)

    private fun subscribeToTextChangedEvents() {
        _onTextChangedEventSubscription = _onTextChangedEvent
                .skip(1) // Value is emitted immediately on subscribe
                .subscribe({ _presenter.onScoreUpdated() }, { }, { })
    }

    private fun unsubscribeFromTextChangedEvents() {
        _onTextChangedEventSubscription?.unsubscribe()
    }
}
