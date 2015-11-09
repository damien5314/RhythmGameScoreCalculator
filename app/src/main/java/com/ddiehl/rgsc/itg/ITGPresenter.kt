package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.RGSC
import com.ddiehl.rgsc.ScoreUpdateListener
import com.ddiehl.rgsc.data.IStorage
import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage

class ITGPresenter(view: ITGView) : ScoreUpdateListener {
    private val logger = RGSC.getLogger()
    private val storage: IStorage = Storage("PREFS_ITG")
    private val view: ITGView = view

    fun onStart() {
        val score: Score = storage.getSavedScore()
        view.displayInput(score)
        updateScore(score, false)
    }

    fun onStop() {
        storage.saveScore(getInput())
    }
    
    override fun onScoreUpdated() {
        updateScore(getInput(), true)
    }

    override fun onScoreClear() {
        view.clearForm()
        updateScore(ITGScore(), false)
    }

    private fun updateScore(score: Score, shouldValidate: Boolean) {
        // Verify input has been submitted
        var invalidInput = false
        if (shouldValidate && score.stepTotal == 0) {
            view.showNoStepsError()
            invalidInput = true
        }
        if (shouldValidate && score.elements[ITGScore.HOLDS]!!.count > score.elements[ITGScore.TOTAL_HOLDS]!!.count) {
            view.showHoldsInvalid()
            invalidInput = true
        }
        if (shouldValidate && score.elements[ITGScore.ROLLS]!!.count > score.elements[ITGScore.TOTAL_ROLLS]!!.count) {
            view.showRollsInvalid()
            invalidInput = true
        }
        if (invalidInput) return
        else view.clearErrors()

        val earned = score.earned
        val potential = score.potential
        // Calculate score percentage rounded to 2 decimal places
        val scorePercent = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00

//        view.showEarned(earned)
//        view.showPotential(potential)
        view.showScoreValues(earned, potential)
        view.showScorePercentage(scorePercent)
        view.showScoreGrade(calculateGrade(scorePercent))
    }

    private fun getInput(): ITGScore {
        val score = ITGScore()
        score.elements[ITGScore.FANTASTICS]!!.count = view.fantastics
        score.elements[ITGScore.EXCELLENTS]!!.count = view.excellents
        score.elements[ITGScore.GREATS]!!.count = view.greats
        score.elements[ITGScore.DECENTS]!!.count = view.decents
        score.elements[ITGScore.WAY_OFFS]!!.count = view.wayoffs
        score.elements[ITGScore.MISSES]!!.count = view.misses
        score.elements[ITGScore.HOLDS]!!.count = view.holds
        score.elements[ITGScore.TOTAL_HOLDS]!!.count = view.totalHolds
        score.elements[ITGScore.MINES]!!.count = view.mines
        score.elements[ITGScore.ROLLS]!!.count = view.rolls
        score.elements[ITGScore.TOTAL_ROLLS]!!.count = view.totalRolls
        logger.d(score.toString())
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
}
