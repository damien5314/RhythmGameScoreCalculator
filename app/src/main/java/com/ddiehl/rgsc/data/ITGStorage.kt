package com.ddiehl.rgsc.data

import android.content.Context
import com.ddiehl.rgsc.ContextProvider
import com.ddiehl.rgsc.itg.ITGScore

class ITGStorage : Storage {
    companion object {
        const val PREFS_ITG = "PREFS_ITG"
    }
    
    private val mContext: Context = ContextProvider.get()

    override fun saveScore(score: ITGScore) {
        val sp = mContext.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE).edit()
        for (element in score.elements) {
            sp.putInt(element.key, element.value.count)
        }
        sp.commit()
    }

    override fun getSavedScore(): ITGScore {
        val sp = mContext.getSharedPreferences(PREFS_ITG, Context.MODE_PRIVATE)
        val score = ITGScore()
        for (element in score.elements) {
            val key = element.key
            score.elements[key]!!.count = sp.getInt(key, 0)
        }
        return score
    }
}