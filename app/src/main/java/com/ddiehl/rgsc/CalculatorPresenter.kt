package com.ddiehl.rgsc

interface CalculatorPresenter {
    fun calculateScore()
    fun loadSavedInput() : Score
    fun saveInput()
}