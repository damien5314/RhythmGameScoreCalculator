package com.ddiehl.rgsc

import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage
import com.ddiehl.rgsc.itg.ITGScore

abstract class ScorePresenter() : ScoreUpdateListener {
    val _logger = RGSC.getLogger()
    abstract val _view: ScoreView
    abstract val _storage: Storage

    abstract fun isScoreValid(score: Score): Boolean

    abstract fun getInput(): Score

    fun onStart() {
        val score: Score = _storage.getSavedScore()
        _view.displayInput(score)
        updateScore(score, false)
    }

    fun onStop() {
        _storage.saveScore(getInput())
    }
    
    override fun onScoreUpdated() {
        updateScore(getInput(), true)
    }

    override fun onScoreClear() {
        _view.clearForm()
        updateScore(ITGScore(), false)
    }

    private fun updateScore(score: Score, shouldValidate: Boolean) {
        var invalidInput = false
        if (shouldValidate) {
            if (score.stepTotal == 0) {
                _view.showNoStepsError()
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
