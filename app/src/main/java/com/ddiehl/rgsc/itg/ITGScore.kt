package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.R
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
            Pair(FANTASTICS, ScoreElement("Fantastics", R.id.fantastics, 5, 5)),
            Pair(EXCELLENTS, ScoreElement("Excellents", R.id.excellents, 4, 5)),
            Pair(GREATS, ScoreElement("Greats", R.id.greats, 2, 5)),
            Pair(DECENTS, ScoreElement("Decents", R.id.decents, 0, 5)),
            Pair(WAY_OFFS, ScoreElement("Way Offs", R.id.wayoffs, -6, 5)),
            Pair(MISSES, ScoreElement("Misses", R.id.misses, -12, 5)),
            Pair(HOLDS, ScoreElement("Holds", R.id.holds, 5, 5)),
            Pair(TOTAL_HOLDS, ScoreElement("Total Holds", R.id.total_holds, 0, 0)),
            Pair(MINES, ScoreElement("Mines", R.id.mines, -6, 0)),
            Pair(ROLLS, ScoreElement("Rolls", R.id.rolls, 5, 5)),
            Pair(TOTAL_ROLLS, ScoreElement("Total Rolls", R.id.total_rolls, 0, 0))
    )
}