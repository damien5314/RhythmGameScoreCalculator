package com.ddiehl.rgsc.ddrsn2

import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.ScoreElement

public class DDRSN2Score() : Score() {
    companion object {
        const val MARVELOUSES = "fantastics"
        const val PERFECTS = "excellents"
        const val GREATS = "greats"
        const val GOODS = "decents"
        const val BOOS = "way offs"
        const val MISSES = "misses"
        const val HOLDS = "holds"
        const val TOTAL_HOLDS = "total holds"
    }

    override val elements: Map<String, ScoreElement> = mapOf(
            Pair(MARVELOUSES, ScoreElement(5, 5)),
            Pair(PERFECTS, ScoreElement(4, 5)),
            Pair(GREATS, ScoreElement(2, 5)),
            Pair(GOODS, ScoreElement(0, 5)),
            Pair(BOOS, ScoreElement(-6, 5)),
            Pair(MISSES, ScoreElement(-12, 5)),
            Pair(HOLDS, ScoreElement(5, 0, false)),
            Pair(TOTAL_HOLDS, ScoreElement(0, 5, false))
    )

    override val earned: Int
        get() {
            val value = stepValue
            var total = 0.0
            total += elements[MARVELOUSES]!!.count * value
            total += elements[PERFECTS]!!.count * (value - 10)
            total += elements[GREATS]!!.count * (value / 2 - 10)
            total += elements[HOLDS]!!.count * value
            return (total.toInt() / 10) * 10 // Round down to the nearest 10
        }

    override val potential: Int = 1000000

    private val stepValue: Double
        get() = 1000000.0 / (stepTotal + elements[TOTAL_HOLDS]!!.count)

    override val grade: String
        get() {
            val score = earned
            if (score == 1000000) return "AAAA"
            if (score > 990000) return "AAA"
            if (score > 950000) return "AA"
            if (score > 900000) return "A"
            if (score > 800000) return "B"
            if (score > 700000) return "C"
            return "D"
        }
}
