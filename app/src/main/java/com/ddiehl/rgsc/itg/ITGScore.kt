package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.data.Score
import com.ddiehl.rgsc.data.ScoreElement

public class ITGScore() : Score() {
    companion object {
        const val FANTASTICS = "fantastics"
        const val EXCELLENTS = "excellents"
        const val GREATS = "greats"
        const val DECENTS = "decents"
        const val WAY_OFFS = "way offs"
        const val MISSES = "misses"
        const val HOLDS = "holds"
        const val TOTAL_HOLDS = "total holds"
        const val MINES = "mines"
        const val ROLLS = "rolls"
        const val TOTAL_ROLLS = "total rolls"
    }

    override val elements: Map<String, ScoreElement> = mapOf(
            Pair(FANTASTICS, ScoreElement("Fantastics", 5, 5)),
            Pair(EXCELLENTS, ScoreElement("Excellents", 4, 5)),
            Pair(GREATS, ScoreElement("Greats", 2, 5)),
            Pair(DECENTS, ScoreElement("Decents", 0, 5)),
            Pair(WAY_OFFS, ScoreElement("Way Offs", -6, 5)),
            Pair(MISSES, ScoreElement("Misses", -12, 5)),
            Pair(HOLDS, ScoreElement("Holds", 5, 5)),
            Pair(TOTAL_HOLDS, ScoreElement("Total Holds", 0, 0)),
            Pair(MINES, ScoreElement("Mines", -6, 0)),
            Pair(ROLLS, ScoreElement("Rolls", 5, 5)),
            Pair(TOTAL_ROLLS, ScoreElement("Total Rolls", 0, 0))
    )
}