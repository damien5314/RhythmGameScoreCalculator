package com.ddiehl.rgsc

import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage

abstract class ScorePresenter() : ScoreUpdateListener {
    val _logger = RGSC.getLogger()
    abstract val _view: ScoreView
    abstract val _storage: Storage

    abstract protected fun getEmptyScore(): Score

    abstract fun isScoreValid(score: Score): Boolean

    abstract fun getInput(): Score

    open fun onStart() {
        val score: Score = _storage.getSavedScore()
        _view.displayInput(score)
        updateScore(score, false)
    }

    open fun onStop() {
        _storage.saveScore(getInput())
    }
    
    override fun onScoreUpdated() {
        updateScore(getInput(), true)
    }

    override fun onScoreClear() {
        _view.clearForm()
        updateScore(getEmptyScore(), false)
    }

    protected open fun updateScore(score: Score, shouldValidate: Boolean) {
        var invalidInput = false
        if (shouldValidate) {
            if (score.stepTotal == 0) {
                // Shouldn't show no steps error when score is auto-calculated
//                _view.showNoStepsError()
                invalidInput = true
            } else if (!isScoreValid(score)) {
                invalidInput = true
            }
        }
        if (invalidInput) return
        else _view.clearErrors()

        _view.showScoreValues(score.earned, score.potential)
        _view.showScorePercentage(score.percent)
        _view.showScoreGrade(score.grade)
    }
}
