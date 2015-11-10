package com.ddiehl.rgsc.data

open class ScoreElement(
        val label: String, val locator: Int, val weight: Int, val bestWeight: Int, val isStep: Boolean = true) {
    var count: Int = 0
}