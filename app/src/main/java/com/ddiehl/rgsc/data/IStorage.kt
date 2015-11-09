package com.ddiehl.rgsc.data

interface IStorage {
    fun getSavedScore(): Score
    fun saveScore(score: Score)
}