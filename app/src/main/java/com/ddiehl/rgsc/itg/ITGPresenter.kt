package com.ddiehl.rgsc.itg

import android.content.Context
import com.ddiehl.rgsc.CalculatorPresenter
import com.ddiehl.rgsc.Score

const val FANTASTICS_WEIGHT = 5
const val EXCELLENTS_WEIGHT = 4
const val GREATS_WEIGHT = 2
const val DECENTS_WEIGHT = 0
const val WAYOFFS_WEIGHT = -6
const val MISSES_WEIGHT = -12
const val HOLDS_WEIGHT = 5
const val MINES_WEIGHT = -6
const val ROLLS_WEIGHT = 5
const val BEST_WEIGHT = FANTASTICS_WEIGHT

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

class ITGPresenter(c: Context, view: ITGView) : CalculatorPresenter {
    private val mContext: Context = c
    private val mView: ITGView = view
    
    override fun calculateScore() {
//        var earnedScore = 0
//        var potentialScore = 0
//        var fantastics = 0
//        var excellents = 0
//        var greats = 0
//        var decents = 0
//        var wayoffs = 0
//        var misses = 0
//        var holds = 0
//        var totalHolds = 0
//        var mines = 0
//        var rolls = 0
//        var totalRolls = 0
//
//        // Read in all of the fields in the form
//        try {
//            fantastics = Integer.valueOf(mFantastics.getText().toString())!!
//            excellents = Integer.valueOf(mExcellents.getText().toString())!!
//            greats = Integer.valueOf(mGreats.getText().toString())!!
//            decents = Integer.valueOf(mDecents.getText().toString())!!
//            wayoffs = Integer.valueOf(mWayoffs.getText().toString())!!
//            misses = Integer.valueOf(mMisses.getText().toString())!!
//            holds = Integer.valueOf(mHolds.getText().toString())!!
//            totalHolds = Integer.valueOf(mTotalHolds.getText().toString())!!
//            mines = Integer.valueOf(mMines.getText().toString())!!
//            rolls = Integer.valueOf(mRolls.getText().toString())!!
//            totalRolls = Integer.valueOf(mTotalRolls.getText().toString())!!
//        } catch (e: NumberFormatException) {
//            Logger.e("Invalid input: ", e)
//            throw e
//        }
//
//        // Verify input has been submitted
//        val stepTotal = fantastics + excellents + greats + decents + wayoffs + misses + holds + totalHolds + rolls + totalRolls
//
//        if (stepTotal != 0) {
//            if (holds > totalHolds) {
//                mHolds.setError(getString(R.string.error_invalid_holds))
//                setErrors()
//                return
//            } else if (rolls > totalRolls) {
//                mRolls.setError(getString(R.string.error_invalid_holds))
//                setErrors()
//                return
//            } else {
//                // Add number from each field multiplied by its weight to earned score
//                earnedScore += fantastics * FANTASTICS_WEIGHT
//                earnedScore += excellents * EXCELLENTS_WEIGHT
//                earnedScore += greats * GREATS_WEIGHT
//                earnedScore += decents * DECENTS_WEIGHT
//                earnedScore += wayoffs * WAYOFFS_WEIGHT
//                earnedScore += misses * MISSES_WEIGHT
//                earnedScore += holds * HOLDS_WEIGHT
//                earnedScore += mines * MINES_WEIGHT
//                earnedScore += rolls * ROLLS_WEIGHT
//
//                // Calculate potential score
//                potentialScore += ((fantastics + excellents + greats + decents + wayoffs + misses) * BEST_WEIGHT) + (totalHolds * HOLDS_WEIGHT) + (totalRolls * ROLLS_WEIGHT)
//
//                // Calculate score percentage rounded to 2 decimal places
//                val scorePercent = (((earnedScore.toDouble() / potentialScore.toDouble()) * 10000).toInt() / 100.00)
//                val df = DecimalFormat("0.00")
//
//                mEarnedScoreValue.setText(earnedScore + "")
//                mPotentialScoreValue.setText(potentialScore + "")
//                mScorePercent.setText(df.format(scorePercent) + "%")
//                mScoreGrade.setText(calculateGrade(scorePercent))
//            }
//        } else {
//            Toast.makeText(getActivity(), R.string.error_no_steps, Toast.LENGTH_SHORT).show()
//        }
    }

    override fun loadSavedInput() : Score {
        val sp = mContext.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE)
        val score = ITGScore()
        score.fantastics = sp.getInt(PREF_FANTASTICS, 0)
        score.excellents = sp.getInt(PREF_EXCELLENTS, 0)
        score.greats = sp.getInt(PREF_GREATS, 0)
        score.decents = sp.getInt(PREF_DECENTS, 0)
        score.wayoffs = sp.getInt(PREF_WAYOFFS, 0)
        score.misses = sp.getInt(PREF_MISSES, 0)
        score.holds = sp.getInt(PREF_HOLDS, 0)
        score.totalHolds = sp.getInt(PREF_TOTAL_HOLDS, 0)
        score.mines = sp.getInt(PREF_MINES, 0)
        score.rolls = sp.getInt(PREF_ROLLS, 0)
        score.totalRolls = sp.getInt(PREF_TOTAL_ROLLS, 0)
        return score
    }

    override fun saveInput() {
        val sp = mContext.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE)
        sp.edit()
                .putInt(PREF_FANTASTICS, mView.fantastics)
                .commit()
    }
}
