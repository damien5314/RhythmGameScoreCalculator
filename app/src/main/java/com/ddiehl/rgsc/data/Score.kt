package com.ddiehl.rgsc.data

abstract class Score {
    abstract val elements: Map<String, ScoreElement>

    open val earned: Int
        get() {
            var result = 0
            for (element in elements.values) result += element.count * element.weight
            return result
        }

    open val potential: Int
        get() {
            var result = 0
            for (element in elements.values) result += element.count * element.bestWeight
            return result
        }

    open val stepTotal: Int
        get() {
            var result = 0
            for (element in elements.values) if (element.isStep) result += element.count
            return result
        }

    open val percent: Double
        get() = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00

    abstract val grade: String

    override fun toString(): String {
        val result = StringBuilder()
        for (element in elements.values) result.append(element.count).append(" ")
        return result.toString()
    }
}