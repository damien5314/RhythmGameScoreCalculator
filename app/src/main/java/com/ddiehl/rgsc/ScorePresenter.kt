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
        // Verify input has been submitted
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

        val earned = score.earned
        val potential = score.potential
        // Calculate score percentage rounded to 2 decimal places
        val scorePercent = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00

        _view.showScoreValues(earned, potential)
        _view.showScorePercentage(scorePercent)
        _view.showScoreGrade(calculateGrade(scorePercent))
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
}
