package com.ddiehl.rgsc.ddrextreme

import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.ScoreElement

public class DDRExScore() : Score() {
    companion object {
        const val MARVELOUSES = "marvelouses"
        const val PERFECTS = "perfects"
        const val GREATS = "greats"
        const val GOODS = "goods"
        const val BOOS = "boos"
        const val MISSES = "misses"
        const val HOLDS = "holds"
        const val TOTAL_HOLDS = "total holds"
    }

    override val elements: Map<String, ScoreElement> = mapOf(
            Pair(MARVELOUSES, ScoreElement(3, 3)),
            Pair(PERFECTS, ScoreElement(2, 3)),
            Pair(GREATS, ScoreElement(1, 3)),
            Pair(GOODS, ScoreElement(0, 3)),
            Pair(BOOS, ScoreElement(-4, 3)),
            Pair(MISSES, ScoreElement(-8, 3)),
            Pair(HOLDS, ScoreElement(5, 0, false)),
            Pair(TOTAL_HOLDS, ScoreElement(0, 5, false))
    )

    var marvelousEnabled = false
        set(value) {
            val bestWeight =
                    if (value) elements[MARVELOUSES]!!.weight
                    else elements[PERFECTS]!!.weight
            elements[PERFECTS]!!.bestWeight = bestWeight
            elements[GREATS]!!.bestWeight = bestWeight
            elements[GOODS]!!.bestWeight = bestWeight
            elements[BOOS]!!.bestWeight = bestWeight
            elements[MISSES]!!.bestWeight = bestWeight
        }

    override val grade: String
        get() {
            val scorePercent = percent
            if (scorePercent == 100.0 && marvelousEnabled) return "AAAA"
            else if (!hasNonPerfectSteps()) return "AAA"
            else if (scorePercent >= 93.0) return "AA"
            else if (scorePercent >= 80.0) return "A"
            else if (scorePercent >= 65.0) return "B"
            else if (scorePercent >= 45.0) return "C"
            else return "D"
        }

    private fun hasNonPerfectSteps(): Boolean {
        return elements[GREATS]!!.count != 0 ||
                elements[GOODS]!!.count != 0 ||
                elements[BOOS]!!.count != 0 ||
                elements[MISSES]!!.count != 0
    }
}
