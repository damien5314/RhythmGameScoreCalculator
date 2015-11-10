package com.ddiehl.rgsc.data

interface IStorage {
    companion object {
        const val PREFS_ITG = "PREFS_ITG"
        const val PREFS_DDREX = "PREFS_DDREX"
    }

    fun getSavedScore(): Score
    fun saveScore(score: Score)
}