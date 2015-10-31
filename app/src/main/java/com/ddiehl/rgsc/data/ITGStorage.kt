package com.ddiehl.rgsc.data

import android.content.Context
import com.ddiehl.rgsc.itg.ITGScore

object ITGStorage : IStorage {
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
    
    lateinit var context: Context
    private var initialized: Boolean = false

    public fun init(c: Context) {
        context = c.applicationContext
        initialized = true
    }

    override fun saveScore(score: ITGScore) {
        if (!initialized) throw IllegalStateException("${javaClass.simpleName} is not yet initialized")
        val sp = context.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE)
        sp.edit()
                .putInt(PREF_FANTASTICS, score.fantastics)
                .putInt(PREF_EXCELLENTS, score.excellents)
                .putInt(PREF_GREATS, score.greats)
                .putInt(PREF_DECENTS, score.decents)
                .putInt(PREF_WAYOFFS, score.wayoffs)
                .putInt(PREF_MISSES, score.misses)
                .putInt(PREF_HOLDS, score.holds)
                .putInt(PREF_TOTAL_HOLDS, score.totalHolds)
                .putInt(PREF_MINES, score.mines)
                .putInt(PREF_ROLLS, score.rolls)
                .putInt(PREF_TOTAL_ROLLS, score.totalRolls)
                .commit()
    }

    override fun getSavedScore(): ITGScore {
        if (!initialized) throw IllegalStateException("${javaClass.simpleName} is not yet initialized")
        val sp = context.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE)
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
}