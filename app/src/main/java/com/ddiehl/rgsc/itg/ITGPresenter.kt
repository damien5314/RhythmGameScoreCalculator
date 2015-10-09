package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.ScoreUpdateListener
import com.ddiehl.rgsc.data.AndroidStorage
import com.ddiehl.rgsc.data.IStorage
import com.orhanobut.logger.Logger
import rx.Observable
import rx.Subscription
import rx.android.widget.OnTextChangeEvent

class ITGPresenter(view: IITGView) : ScoreUpdateListener {
    private val storage: IStorage = AndroidStorage
    private val view: IITGView = view

    // Rx
    private lateinit var _onTextChangedEvent: Observable<OnTextChangeEvent>
    private var _onTextChangedEventSubscription: Subscription? = null

    fun onStart() {
        _onTextChangedEvent = view.getTextChangedObservable()
        val score: ITGScore = storage.getSavedScore()
        view.displayInput(score)
        updateScore(score, false)
        subscribeToTextChangedEvents()
    }

    fun onStop() {
        storage.saveScore(getInput())
        unsubscribeFromTextChangedEvents()
    }
    
    override fun onScoreUpdated() {
        updateScore(getInput(), true)
    }

    override fun onScoreClear() {
        unsubscribeFromTextChangedEvents()
        view.clearForm()
        updateScore(ITGScore(), false)
        subscribeToTextChangedEvents()
    }

    private fun updateScore(score: ITGScore, shouldValidate: Boolean) {
        // Verify input has been submitted
        var invalidInput = false
        if (shouldValidate && score.stepTotal == 0) {
            view.showNoStepsError()
            invalidInput = true
        }
        if (score.holds > score.totalHolds) {
            view.showHoldsInvalid()
            invalidInput = true
        }
        if (score.rolls > score.totalRolls) {
            view.showRollsInvalid()
            invalidInput = true
        }
        if (invalidInput) return
        else view.clearErrors()

        val earned = score.earned
        val potential = score.potential
        // Calculate score percentage rounded to 2 decimal places
        val scorePercent = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00

        view.showEarned(earned)
        view.showPotential(potential)
        view.showScorePercentage(scorePercent)
        view.showScoreGrade(calculateGrade(scorePercent))
    }

    private fun getInput(): ITGScore {
        val score = ITGScore()
        score.fantastics = view.fantastics
        score.excellents = view.excellents
        score.greats = view.greats
        score.decents = view.decents
        score.wayoffs = view.wayoffs
        score.misses = view.misses
        score.holds = view.holds
        score.totalHolds = view.totalHolds
        score.mines = view.mines
        score.rolls = view.rolls
        score.totalRolls = view.totalRolls
        Logger.d(score.toString())
        return score
    }

    private fun calculateGrade(score: Double): String {
        if (score == 100.0) return "****"
        else if (score > 99.0) return "***"
        else if (score > 98.0) return "**"
        else if (score > 96.0) return "*"
        else if (score > 94.0) return "S+"
        else if (score > 92.0) return "S"
        else if (score > 89.0) return "S-"
        else if (score > 86.0) return "A+"
        else if (score > 83.0) return "A"
        else if (score > 80.0) return "A-"
        else if (score > 76.0) return "B+"
        else if (score > 72.0) return "B"
        else if (score > 68.0) return "B-"
        else if (score > 64.0) return "C+"
        else if (score > 60.0) return "C"
        else if (score > 55.0) return "C-"
        else return "D"
    }

    private fun subscribeToTextChangedEvents() {
        _onTextChangedEventSubscription = _onTextChangedEvent.subscribe()
    }

    private fun unsubscribeFromTextChangedEvents() {
        _onTextChangedEventSubscription?.unsubscribe()
    }
}
