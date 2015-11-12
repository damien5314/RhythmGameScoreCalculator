package com.ddiehl.rgsc.data

interface Storage {
    companion object {
        const val PREFS_APP = "PREFS_APP"
        const val PREFS_ITG = "PREFS_ITG"
        const val PREFS_DDREX = "PREFS_DDREX"
        const val PREFS_DDRSN2 = "PREFS_DDRSN2"
    }

    fun getSavedScore(): Score
    fun saveScore(score: Score)
}