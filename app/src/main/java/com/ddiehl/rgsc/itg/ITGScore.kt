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
            Pair(FANTASTICS, ScoreElement(5, 5)),
            Pair(EXCELLENTS, ScoreElement(4, 5)),
            Pair(GREATS, ScoreElement(2, 5)),
            Pair(DECENTS, ScoreElement(0, 5)),
            Pair(WAY_OFFS, ScoreElement(-6, 5)),
            Pair(MISSES, ScoreElement(-12, 5)),
            Pair(HOLDS, ScoreElement(5, 0, false)),
            Pair(TOTAL_HOLDS, ScoreElement(0, 5, false)),
            Pair(MINES, ScoreElement(-6, 0, false)),
            Pair(ROLLS, ScoreElement(5, 0, false)),
            Pair(TOTAL_ROLLS, ScoreElement(0, 5, false))
    )

    override val grade: String
        get() {
            val scorePercent = percent
            if (scorePercent == 100.0) return "****"
            else if (scorePercent > 99.0) return "***"
            else if (scorePercent > 98.0) return "**"
            else if (scorePercent > 96.0) return "*"
            else if (scorePercent > 94.0) return "S+"
            else if (scorePercent > 92.0) return "S"
            else if (scorePercent > 89.0) return "S-"
            else if (scorePercent > 86.0) return "A+"
            else if (scorePercent > 83.0) return "A"
            else if (scorePercent > 80.0) return "A-"
            else if (scorePercent > 76.0) return "B+"
            else if (scorePercent > 72.0) return "B"
            else if (scorePercent > 68.0) return "B-"
            else if (scorePercent > 64.0) return "C+"
            else if (scorePercent > 60.0) return "C"
            else if (scorePercent > 55.0) return "C-"
            else return "D"
        }
}
