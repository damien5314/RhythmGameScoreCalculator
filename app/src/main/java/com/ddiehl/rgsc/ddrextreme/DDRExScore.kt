package com.ddiehl.rgsc.ddrextreme

import com.ddiehl.rgsc.R
import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.ScoreElement

public class DDRExScore() : Score() {
    companion object {
        const val MARVELLOUSES = "marvellouses"
        const val PERFECTS = "perfects"
        const val GREATS = "greats"
        const val GOODS = "goods"
        const val BOOS = "boos"
        const val MISSES = "misses"
        const val HOLDS = "holds"
        const val TOTAL_HOLDS = "total holds"
    }

    override val elements: Map<String, ScoreElement> = mapOf(
            Pair(MARVELLOUSES, ScoreElement(R.id.ddrex_marvellouses, R.string.ddrex_marvellouses, 0, 3, 3)),
            Pair(PERFECTS, ScoreElement(R.id.ddrex_perfects, R.string.ddrex_perfects, 0, 2, 3)),
            Pair(GREATS, ScoreElement(R.id.ddrex_greats, R.string.ddrex_greats, 0, 1, 3)),
            Pair(GOODS, ScoreElement(R.id.ddrex_goods, R.string.ddrex_goods, 0, 0, 3)),
            Pair(BOOS, ScoreElement(R.id.ddrex_boos, R.string.ddrex_boos, 0, -4, 3)),
            Pair(MISSES, ScoreElement(R.id.ddrex_misses, R.string.ddrex_misses, 0, -8, 3)),
            Pair(HOLDS, ScoreElement(R.id.ddrex_holds, R.string.ddrex_holds, 1, 5, 0)),
            Pair(TOTAL_HOLDS, ScoreElement(R.id.ddrex_total_holds, R.string.ddrex_total_holds, 1, 0, 5))
    )

    override val grade: String
        get() {
            val scorePercent = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00
            if (scorePercent == 100.0) return "AAA"
            else if (scorePercent >= 93.0) return "AA"
            else if (scorePercent >= 80.0) return "A"
            else if (scorePercent >= 65.0) return "B"
            else if (scorePercent >= 45.0) return "C"
            else return "D"
        }
}
