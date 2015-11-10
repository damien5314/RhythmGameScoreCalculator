package com.ddiehl.rgsc

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import butterknife.bindView
import com.jakewharton.rxbinding.widget.RxTextView
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

abstract class ScoreViewFragment() : Fragment(), ScoreView {
    companion object {
        private val SCORE_CALC_DELAY = 500L
        private val CLEAR_ALL_TIME_THRESHOLD_MS = 1500L
    }

    protected val _logger = RGSC.getLogger()
    protected lateinit var _presenter: ScorePresenter
    protected val _handler = Handler()

    protected val _scoreEntryScrollView: ScrollView by bindView(R.id.score_entry_scrollview)
    protected lateinit var _scoreEntryFields: List<EditText>

    protected val _calculatedScoreArea: ViewGroup by bindView(R.id.calculated_score)
    protected val _scoreValueArea: TextView by bindView(R.id.score_value_area)
    protected val _scorePercent: TextView by bindView(R.id.score_percent)
    protected val _scoreGrade: TextView by bindView(R.id.score_grade)

    protected val _keypadLayout: ViewGroup by bindView(R.id.keypad_layout)
    protected val _deleteButton: Button by bindView(R.id.keypad_delete)
    protected val _nextButton: Button by bindView(R.id.keypad_next)
    protected val _keypad: ViewGroup by bindView(R.id.keypad_layout)
    protected val _keypad_1: Button by bindView(R.id.keypad_1)
    protected val _keypad_2: Button by bindView(R.id.keypad_2)
    protected val _keypad_3: Button by bindView(R.id.keypad_3)
    protected val _keypad_4: Button by bindView(R.id.keypad_4)
    protected val _keypad_5: Button by bindView(R.id.keypad_5)
    protected val _keypad_6: Button by bindView(R.id.keypad_6)
    protected val _keypad_7: Button by bindView(R.id.keypad_7)
    protected val _keypad_8: Button by bindView(R.id.keypad_8)
    protected val _keypad_9: Button by bindView(R.id.keypad_9)
    protected val _keypad_0: Button by bindView(R.id.keypad_0)
    protected lateinit var _keypadButtons: List<Button>

    protected lateinit var _onTextChangedEvent: Observable<CharSequence>
    protected var _onTextChangedEventSubscription: Subscription? = null

    protected var _currentFocusedField: EditText? = null
    protected var _deleteTapCounter = 0
    protected val _deleteButtonRunnable = { _deleteTapCounter = 0 }

    abstract protected fun getPresenter(): ScorePresenter

    abstract protected fun getScoreEntryFields(): List<EditText>

    abstract protected fun getTextChangedObservables(): List<Observable<CharSequence>>

    override abstract fun clearErrors()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = getPresenter()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_inthegroove, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _scoreEntryFields = getScoreEntryFields()
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

    protected fun getInputFrom(t: EditText): Int {
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

    protected val keypadNumberScrollUpOnFocusChangeListener = View.OnFocusChangeListener {
        view, visible ->
        showKeypadListener(view, visible)
        _scoreEntryScrollView.scrollTo(0, 0)
    }

    private fun setOnFocusListeners() {
        for (et in _scoreEntryFields) et.onFocusChangeListener = keypadNumberOnFocusChangeListener
        // FIXME - Make these work again
//        _fantastics.onFocusChangeListener = keypadNumberScrollUpOnFocusChangeListener
//        _holds.onFocusChangeListener = keypadNumberScrollUpOnFocusChangeListener
    }

    protected fun stripZero(i: Int?): String {
        if (i == null || i == 0) return ""
        else return i.toString()
    }

    override fun showNoStepsError() {
        Snackbar.make(view, R.string.error_no_steps, Snackbar.LENGTH_SHORT).show()
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

    override fun showScoreError() {
        val scoreValueFormatter = getString(R.string.score_value_formatter)
        _scoreValueArea.text = scoreValueFormatter.format(0, 0, 0)
        _scorePercent.text = ""
        _scoreGrade.text = ""
    }

    override fun getTextChangedObservable(): Observable<CharSequence> {
        return Observable.merge(getTextChangedObservables())
                .debounce(SCORE_CALC_DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }

    protected fun getTextChangedObservable(t: EditText): Observable<CharSequence> = RxTextView.textChanges(t)

    private fun subscribeToTextChangedEvents() {
        _onTextChangedEventSubscription =
                _onTextChangedEvent.subscribe({ _presenter.onScoreUpdated() }, { }, { })
    }

    private fun unsubscribeFromTextChangedEvents() {
        _onTextChangedEventSubscription?.unsubscribe()
    }
}
