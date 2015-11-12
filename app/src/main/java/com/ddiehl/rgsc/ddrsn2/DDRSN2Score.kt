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

    override val grade: String
        get() {
            val scorePercent = percent
            return "D"
        }
}
