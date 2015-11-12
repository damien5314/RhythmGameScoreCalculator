package com.ddiehl.rgsc.ddrsn2

import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.data.AndroidStorage
import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage

class DDRSN2Presenter(override val _view: DDRSN2View) : ScorePresenter() {
    override val _storage: Storage = AndroidStorage(Storage.PREFS_DDRSN2)

    override fun getEmptyScore(): Score {
        return DDRSN2Score()
    }

    override fun getInput(): DDRSN2Score {
        val score = DDRSN2Score()
        score.elements[DDRSN2Score.MARVELOUSES]!!.count = _view.marvelouses
        score.elements[DDRSN2Score.PERFECTS]!!.count = _view.perfects
        score.elements[DDRSN2Score.GREATS]!!.count = _view.greats
        score.elements[DDRSN2Score.GOODS]!!.count = _view.goods
        score.elements[DDRSN2Score.BOOS]!!.count = _view.boos
        score.elements[DDRSN2Score.MISSES]!!.count = _view.misses
        score.elements[DDRSN2Score.HOLDS]!!.count = _view.holds
        score.elements[DDRSN2Score.TOTAL_HOLDS]!!.count = _view.totalHolds
        _logger.d(score.toString())
        return score
    }

    override fun isScoreValid(score: Score): Boolean {
        if (score.elements[DDRSN2Score.HOLDS]!!.count > score.elements[DDRSN2Score.TOTAL_HOLDS]!!.count) {
            _view.showHoldsInvalid()
            return false
        }
        return true
    }
}