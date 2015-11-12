package com.ddiehl.rgsc.ddrsn2

import com.ddiehl.rgsc.R
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
            Pair(MARVELOUSES, ScoreElement(R.id.ddrsn2_marvelouses, R.string.ddrsn2_marvelouses, 0, 5, 5)),
            Pair(PERFECTS, ScoreElement(R.id.ddrsn2_perfects, R.string.ddrsn2_perfects, 0, 4, 5)),
            Pair(GREATS, ScoreElement(R.id.ddrsn2_greats, R.string.ddrsn2_greats, 0, 2, 5)),
            Pair(GOODS, ScoreElement(R.id.ddrsn2_goods, R.string.ddrsn2_goods, 0, 0, 5)),
            Pair(BOOS, ScoreElement(R.id.ddrsn2_boos, R.string.ddrsn2_boos, 0, -6, 5)),
            Pair(MISSES, ScoreElement(R.id.ddrsn2_misses, R.string.ddrsn2_misses, 0, -12, 5)),
            Pair(HOLDS, ScoreElement(R.id.ddrsn2_holds, R.string.ddrsn2_holds, 1, 5, 0)),
            Pair(TOTAL_HOLDS, ScoreElement(R.id.ddrsn2_total_holds, R.string.ddrsn2_total_holds, 1, 0, 5))
    )

    override val grade: String
        get() {
            val scorePercent = percent
            return "D"
        }
}
