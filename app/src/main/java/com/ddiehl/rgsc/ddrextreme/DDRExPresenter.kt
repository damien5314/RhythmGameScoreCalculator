package com.ddiehl.rgsc.ddrextreme

import android.content.Context
import com.ddiehl.rgsc.ContextProvider
import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.data.AndroidStorage
import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage

class DDRExPresenter(override val _view: DDRExView) : ScorePresenter() {
    companion object {
        val PREF_MARVELOUS_SWITCH = "pref_marvelous_switch"
    }

    override val _storage: Storage = AndroidStorage(Storage.PREFS_DDREX)

    override fun onStart() {
        super.onStart()
        // Load state of marvelous switch
        val sp = ContextProvider.get().getSharedPreferences(Storage.PREFS_DDREX, Context.MODE_PRIVATE)
        val marvelousEnabled = sp.getBoolean(PREF_MARVELOUS_SWITCH, false)
        _view.marvellousesEnabled = marvelousEnabled
    }

    override fun onStop() {
        // Save state of marvelous switch
        val sp = ContextProvider.get().getSharedPreferences(Storage.PREFS_DDREX, Context.MODE_PRIVATE)
        sp.edit().putBoolean(PREF_MARVELOUS_SWITCH, _view.marvellousesEnabled).apply()
        super.onStop()
    }

    override fun getEmptyScore(): Score {
        return DDRExScore()
    }

    override fun getInput(): DDRExScore {
        val score = DDRExScore()
        score.elements[DDRExScore.MARVELLOUSES]!!.count = _view.marvellouses
        score.elements[DDRExScore.PERFECTS]!!.count = _view.perfects
        score.elements[DDRExScore.GREATS]!!.count = _view.greats
        score.elements[DDRExScore.GOODS]!!.count = _view.goods
        score.elements[DDRExScore.BOOS]!!.count = _view.boos
        score.elements[DDRExScore.MISSES]!!.count = _view.misses
        score.elements[DDRExScore.HOLDS]!!.count = _view.holds
        score.elements[DDRExScore.TOTAL_HOLDS]!!.count = _view.totalHolds
        score.marvelousEnabled = _view.marvellousesEnabled
        return score
    }

    override fun isScoreValid(score: Score): Boolean {
        if (score.elements[DDRExScore.HOLDS]!!.count > score.elements[DDRExScore.TOTAL_HOLDS]!!.count) {
            _view.showHoldsInvalid()
            return false
        }
        return true
    }
}