package com.ddiehl.rgsc.data

import android.content.Context
import com.ddiehl.rgsc.itg.ITGPresenter
import com.ddiehl.rgsc.itg.ITGScore

object AndroidStorage : IStorage {
    lateinit var context: Context
    private var initialized: Boolean = false

    public fun init(c: Context) {
        context = c.applicationContext
        initialized = true
    }

    override fun saveScore(score: ITGScore) {
        if (!initialized) throw IllegalStateException("${javaClass.simpleName} is not yet initialized")
        val sp = context.getSharedPreferences(ITGPresenter.ITGPresenter.PREFS_ITG, Context.MODE_PRIVATE)
        sp.edit()
                .putInt(ITGPresenter.ITGPresenter.PREF_FANTASTICS, score.fantastics)
                .putInt(ITGPresenter.ITGPresenter.PREF_EXCELLENTS, score.excellents)
                .putInt(ITGPresenter.ITGPresenter.PREF_GREATS, score.greats)
                .putInt(ITGPresenter.ITGPresenter.PREF_DECENTS, score.decents)
                .putInt(ITGPresenter.ITGPresenter.PREF_WAYOFFS, score.wayoffs)
                .putInt(ITGPresenter.ITGPresenter.PREF_MISSES, score.misses)
                .putInt(ITGPresenter.ITGPresenter.PREF_HOLDS, score.holds)
                .putInt(ITGPresenter.ITGPresenter.PREF_TOTAL_HOLDS, score.totalHolds)
                .putInt(ITGPresenter.ITGPresenter.PREF_MINES, score.mines)
                .putInt(ITGPresenter.ITGPresenter.PREF_ROLLS, score.rolls)
                .putInt(ITGPresenter.ITGPresenter.PREF_TOTAL_ROLLS, score.totalRolls)
                .commit()
    }

    override fun getSavedScore(): ITGScore {
        if (!initialized) throw IllegalStateException("${javaClass.simpleName} is not yet initialized")
        val sp = context.getSharedPreferences(ITGPresenter.ITGPresenter.PREFS_ITG, Context.MODE_PRIVATE)
        val score = ITGScore()
        score.fantastics = sp.getInt(ITGPresenter.ITGPresenter.PREF_FANTASTICS, 0)
        score.excellents = sp.getInt(ITGPresenter.ITGPresenter.PREF_EXCELLENTS, 0)
        score.greats = sp.getInt(ITGPresenter.ITGPresenter.PREF_GREATS, 0)
        score.decents = sp.getInt(ITGPresenter.ITGPresenter.PREF_DECENTS, 0)
        score.wayoffs = sp.getInt(ITGPresenter.ITGPresenter.PREF_WAYOFFS, 0)
        score.misses = sp.getInt(ITGPresenter.ITGPresenter.PREF_MISSES, 0)
        score.holds = sp.getInt(ITGPresenter.ITGPresenter.PREF_HOLDS, 0)
        score.totalHolds = sp.getInt(ITGPresenter.ITGPresenter.PREF_TOTAL_HOLDS, 0)
        score.mines = sp.getInt(ITGPresenter.ITGPresenter.PREF_MINES, 0)
        score.rolls = sp.getInt(ITGPresenter.ITGPresenter.PREF_ROLLS, 0)
        score.totalRolls = sp.getInt(ITGPresenter.ITGPresenter.PREF_TOTAL_ROLLS, 0)
        return score
    }

}