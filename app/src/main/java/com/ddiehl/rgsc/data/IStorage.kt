package com.ddiehl.rgsc.data

import com.ddiehl.rgsc.itg.ITGScore

interface IStorage {
    fun getSavedScore(): ITGScore
    fun saveScore(score: ITGScore)
}