package com.ddiehl.rgsc.data

import android.content.Context
import com.ddiehl.rgsc.ContextProvider

class Storage(val prefKey: String) : IStorage {
    private val mContext: Context = ContextProvider.get()

    override fun saveScore(score: Score) {
        val sp = mContext.getSharedPreferences(prefKey, Context.MODE_PRIVATE).edit()
        for (element in score.elements) {
            sp.putInt(element.key, element.value.count)
        }
        sp.commit()
    }

    override fun getSavedScore(): Score {
        val sp = mContext.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
        val score = Score()
        for (element in score.elements) {
            val key = element.key
            score.elements[key]!!.count = sp.getInt(key, 0)
        }
        return score
    }
}