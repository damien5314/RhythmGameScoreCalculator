package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.ScorePresenter
import com.ddiehl.rgsc.data.AndroidStorage
import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.Storage

class ITGPresenter(override val _view: ITGView) : ScorePresenter() {
    override val _storage: Storage = AndroidStorage(Storage.PREFS_ITG)

    override fun getInput(): ITGScore {
        val score = ITGScore()
        score.elements[ITGScore.FANTASTICS]!!.count = _view.fantastics
        score.elements[ITGScore.EXCELLENTS]!!.count = _view.excellents
        score.elements[ITGScore.GREATS]!!.count = _view.greats
        score.elements[ITGScore.DECENTS]!!.count = _view.decents
        score.elements[ITGScore.WAY_OFFS]!!.count = _view.wayoffs
        score.elements[ITGScore.MISSES]!!.count = _view.misses
        score.elements[ITGScore.HOLDS]!!.count = _view.holds
        score.elements[ITGScore.TOTAL_HOLDS]!!.count = _view.totalHolds
        score.elements[ITGScore.MINES]!!.count = _view.mines
        score.elements[ITGScore.ROLLS]!!.count = _view.rolls
        score.elements[ITGScore.TOTAL_ROLLS]!!.count = _view.totalRolls
        _logger.d(score.toString())
        return score
    }

    override fun isScoreValid(score: Score): Boolean {
        if (score.elements[ITGScore.HOLDS]!!.count > score.elements[ITGScore.TOTAL_HOLDS]!!.count) {
            _view.showHoldsInvalid()
            return false
        }
        if (score.elements[ITGScore.ROLLS]!!.count > score.elements[ITGScore.TOTAL_ROLLS]!!.count) {
            _view.showRollsInvalid()
            return false
        }
        return true
    }
}