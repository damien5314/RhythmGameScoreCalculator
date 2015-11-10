package com.ddiehl.rgsc.data

import android.content.Context
import com.ddiehl.rgsc.ContextProvider
import com.ddiehl.rgsc.itg.ITGScore

class AndroidStorage(private val _prefKey: String) : Storage {
    private val _context: Context = ContextProvider.get()

    override fun saveScore(score: Score) {
        val sp = _context.getSharedPreferences(_prefKey, Context.MODE_PRIVATE).edit()
        for (element in score.elements) {
            sp.putInt(element.key, element.value.count)
        }
        sp.commit()
    }

    override fun getSavedScore(): Score {
        val sp = _context.getSharedPreferences(_prefKey, Context.MODE_PRIVATE)
        val score = when (_prefKey) {
            Storage.PREFS_ITG -> ITGScore()
            else -> Score()
        }
        for (element in score.elements) {
            val key = element.key
            score.elements[key]!!.count = sp.getInt(key, 0)
        }
        return score
    }
}