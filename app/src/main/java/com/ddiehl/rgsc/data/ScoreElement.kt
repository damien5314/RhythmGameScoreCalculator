package com.ddiehl.rgsc.data

open class ScoreElement(val weight: Int, var bestWeight: Int, val isStep: Boolean = true) {
    var count: Int = 0
}