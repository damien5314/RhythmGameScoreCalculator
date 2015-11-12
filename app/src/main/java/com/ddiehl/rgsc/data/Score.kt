package com.ddiehl.rgsc.data

abstract class Score {
    open val elements: Map<String, ScoreElement> = emptyMap()

    val earned: Int
        get() {
            var result = 0
            for (element in elements.values) result += element.count * element.weight
            return result
        }

    val potential: Int
        get() {
            var result = 0
            for (element in elements.values) result += element.count * element.bestWeight
            return result
        }

    val stepTotal: Int
        get() {
            var result = 0
            for (element in elements.values) if (element.isStep) result += element.count
            return result
        }

    val percent: Double
        get() = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00

    abstract val grade: String

    override fun toString(): String {
        val result = StringBuilder()
        for (element in elements.values) result.append(element.count).append(" ")
        return result.toString()
    }
}