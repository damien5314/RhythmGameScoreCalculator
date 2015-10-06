package com.ddiehl.rgsc.itg

import android.content.Context
import java.text.DecimalFormat

class ITGPresenter(c: Context, view: ITGView) {
    object ITGPresenter {
        const val PREFS_ITG = "PREFS_ITG"
        const val PREF_FANTASTICS = "PREF_FANTASTICS"
        const val PREF_EXCELLENTS = "PREF_EXCELLENTS"
        const val PREF_GREATS = "PREF_GREATS"
        const val PREF_DECENTS = "PREF_DECENTS"
        const val PREF_WAYOFFS = "PREF_WAYOFFS"
        const val PREF_MISSES = "PREF_MISSES"
        const val PREF_HOLDS = "PREF_HOLDS"
        const val PREF_TOTAL_HOLDS = "PREF_TOTAL_HOLDS"
        const val PREF_MINES = "PREF_MINES"
        const val PREF_ROLLS = "PREF_ROLLS"
        const val PREF_TOTAL_ROLLS = "PREF_TOTAL_ROLLS"
    }

    private val context: Context = c
    private val view: ITGView = view

    fun onStart() {
        val score: ITGScore = loadSavedInput()
        view.displayInput(score)
        updateScore(score)
    }

    fun onStop() {
        saveInput()
    }

    private fun updateScore(score: ITGScore) {
        // Verify input has been submitted
        var invalidInput = false
        if (score.stepTotal == 0) {
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

    private fun loadSavedInput() : ITGScore {
        val sp = context.getSharedPreferences(ITGPresenter.PREFS_ITG, Context.MODE_PRIVATE)
        val score = ITGScore()
        score.fantastics = sp.getInt(ITGPresenter.PREF_FANTASTICS, 0)
        score.excellents = sp.getInt(ITGPresenter.PREF_EXCELLENTS, 0)
        score.greats = sp.getInt(ITGPresenter.PREF_GREATS, 0)
        score.decents = sp.getInt(ITGPresenter.PREF_DECENTS, 0)
        score.wayoffs = sp.getInt(ITGPresenter.PREF_WAYOFFS, 0)
        score.misses = sp.getInt(ITGPresenter.PREF_MISSES, 0)
        score.holds = sp.getInt(ITGPresenter.PREF_HOLDS, 0)
        score.totalHolds = sp.getInt(ITGPresenter.PREF_TOTAL_HOLDS, 0)
        score.mines = sp.getInt(ITGPresenter.PREF_MINES, 0)
        score.rolls = sp.getInt(ITGPresenter.PREF_ROLLS, 0)
        score.totalRolls = sp.getInt(ITGPresenter.PREF_TOTAL_ROLLS, 0)
        return score
    }

    private fun saveInput() {
        val sp = context.getSharedPreferences(ITGPresenter.PREFS_ITG, Context.MODE_PRIVATE)
        sp.edit()
                .putInt(ITGPresenter.PREF_FANTASTICS, view.fantastics)
                .putInt(ITGPresenter.PREF_EXCELLENTS, view.excellents)
                .putInt(ITGPresenter.PREF_GREATS, view.greats)
                .putInt(ITGPresenter.PREF_DECENTS, view.decents)
                .putInt(ITGPresenter.PREF_WAYOFFS, view.wayoffs)
                .putInt(ITGPresenter.PREF_MISSES, view.misses)
                .putInt(ITGPresenter.PREF_HOLDS, view.holds)
                .putInt(ITGPresenter.PREF_TOTAL_HOLDS, view.totalHolds)
                .putInt(ITGPresenter.PREF_MINES, view.mines)
                .putInt(ITGPresenter.PREF_ROLLS, view.rolls)
                .putInt(ITGPresenter.PREF_TOTAL_ROLLS, view.totalRolls)
                .commit()
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
