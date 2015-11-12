package com.ddiehl.rgsc

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.bindView
import com.ddiehl.rgsc.data.Storage
import com.ddiehl.rgsc.utils.getChildren
import com.jakewharton.rxbinding.widget.RxTextView
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit

abstract class ScoreViewFragment() : Fragment(), ScoreView {
    companion object {
        private val SCORE_CALC_DELAY = 500L
        private val CLEAR_ALL_TIME_THRESHOLD_MS = 1500L
        private val PREF_DELETE_KEY_EXPLANATION_DISPLAYED = "PREF_DELETE_KEY_EXPLANATION_DISPLAYED"
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
    protected var _deleteKeyExplanationShown: Boolean? = null

    abstract protected fun getPresenter(): ScorePresenter

    override abstract fun clearErrors()

    protected fun getInputFrom(t: EditText): Int {
        val s = t.text.toString()
        return if (s == "") 0 else s.toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = getPresenter()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.calculator, null)
        val scoreEntryContainer = view.findViewById(R.id.score_entry_scrollview) as ViewGroup
        val scoreEntryLayout = inflater.inflate(calculatorLayoutResId, scoreEntryContainer, false)
        scoreEntryContainer.addView(scoreEntryLayout)
        return view
    }

    abstract protected val calculatorLayoutResId: Int

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initializeView()
    }

    override fun onStart() {
        super.onStart()
        initializeView()
        _presenter.onStart()
        subscribeToTextChangedEvents()
    }

    override fun onStop() {
        unsubscribeFromTextChangedEvents()
        _presenter.onStop()
        super.onStop()
    }

    private fun initializeView() {
        _scoreEntryFields = getScoreEntryFields()
        _keypadButtons = listOf(_keypad_0, _keypad_1, _keypad_2, _keypad_3, _keypad_4,
                _keypad_5, _keypad_6, _keypad_7, _keypad_8, _keypad_9)
        if (shouldHideKeyboard()) _keypad.visibility = View.GONE
        _onTextChangedEvent = getTextChangedObservable()
//        disableEditText()
        setOnFocusListeners()
        setKeypadClickListeners()
        _calculatedScoreArea.setOnClickListener {
            if (shouldHideKeyboard()) _currentFocusedField?.clearFocus()
        }
    }

    protected fun getScoreEntryFields(): List<EditText> {
        val parentContainer = _scoreEntryScrollView.getChildAt(0) as TableLayout
        val list = ArrayList<EditText>()
        for (row in parentContainer.getChildren()) {
            for (element in (row as TableRow).getChildren()) {
                // Check if this is actually a score entry element
                if (element is ViewGroup) {
                    val child = element.getChildAt(1)
                    if (child is EditText) list.add(child)
                }
            }
        }
        return list
    }

    private fun setKeypadClickListeners() {
        for (button in _keypadButtons) button.setOnClickListener(digitClickListener)
        _deleteButton.setOnClickListener {
            showDeleteKeyExplanation()
            _handler.removeCallbacks(_deleteButtonRunnable)
            _currentFocusedField?.setText("")
            _deleteTapCounter++
            if (_deleteTapCounter >= 3) {
                _deleteTapCounter = 0
                _presenter.onScoreClear()
            } else {
                _handler.postDelayed(_deleteButtonRunnable, CLEAR_ALL_TIME_THRESHOLD_MS)
            }
        }
        _nextButton.setOnClickListener {
            val nextFocusId = _currentFocusedField?.nextFocusForwardId
            if (nextFocusId != null) activity.findViewById(nextFocusId)?.requestFocus()
        }
    }

    private val digitClickListener: View.OnClickListener = View.OnClickListener {
        val text: String = _currentFocusedField?.text.toString()
        if (_currentFocusedField != null) {
            val selectionLength =
                    _currentFocusedField!!.selectionEnd - _currentFocusedField!!.selectionStart
            if (selectionLength == 0) _currentFocusedField!!.setText(text + it.tag)
            else _currentFocusedField!!.setText(it.tag as String)
            _currentFocusedField!!.setSelection(_currentFocusedField?.text?.length ?: 0);
        }
    }

//    private fun disableEditText() {
//        for (t in _scoreEntryFields) {
//            t.setRawInputType(InputType.TYPE_CLASS_NUMBER)
//            t.setTextIsSelectable(true)
//        }
//    }

    private fun showKeypad(visible: Boolean) {
        _keypad.visibility = if (!visible && shouldHideKeyboard()) View.GONE else View.VISIBLE
    }

    private fun setOnFocusListeners() {
        for (et in _scoreEntryFields) et.onFocusChangeListener = keypadNumberOnFocusChangeListener
    }

    private val keypadNumberOnFocusChangeListener = View.OnFocusChangeListener {
        view, visible ->
        _currentFocusedField = if (visible) view as EditText else null
        showKeypad(visible)
        val parentLayout = view.parent.parent as View
        if (visible) scrollToView(_scoreEntryScrollView, parentLayout)
    }

    private fun scrollToView(scrollView: ScrollView, view: View) {
        if (!scrollView.isViewVisible(view))
        scrollView.smoothScrollTo(0, view.top)
    }

    private fun ScrollView.isViewVisible(view: View): Boolean {
        val scrollBounds = Rect();
        getDrawingRect(scrollBounds);
        val top = view.y;
        val bottom = top + view.height;
        return scrollBounds.top <= top && scrollBounds.bottom >= bottom
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
        _scoreValueArea.text = scoreValueFormatter.format("-", "-", "-")
        _scorePercent.text = getString(R.string.score_percent_error)
        _scoreGrade.text = getString(R.string.score_grade_error)
    }

    override fun getTextChangedObservable(): Observable<CharSequence> {
        return Observable.merge(_scoreEntryFields.map { RxTextView.textChanges(it) })
                .debounce(SCORE_CALC_DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }

    private fun subscribeToTextChangedEvents() {
        _onTextChangedEventSubscription =
                _onTextChangedEvent.subscribe({ _presenter.onScoreUpdated() }, { }, { })
    }

    private fun unsubscribeFromTextChangedEvents() {
        _onTextChangedEventSubscription?.unsubscribe()
    }

    private fun shouldHideKeyboard(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    override fun showDeleteKeyExplanation() {
        // Get value from shared preferences
        if (_deleteKeyExplanationShown == null) {
            val prefs = context.getSharedPreferences(Storage.PREFS_APP, Context.MODE_PRIVATE)
            _deleteKeyExplanationShown = prefs.getBoolean(PREF_DELETE_KEY_EXPLANATION_DISPLAYED, false)
        }
        // If we have never shown the explanation, show it
        if (_deleteKeyExplanationShown == false) {
            Toast.makeText(context, R.string.delete_key_explanation, Toast.LENGTH_LONG).show()
            // Update value in shared preferences
            _deleteKeyExplanationShown = true
            val prefs = context.getSharedPreferences(Storage.PREFS_APP, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(PREF_DELETE_KEY_EXPLANATION_DISPLAYED, true).apply()
        }
    }
}
