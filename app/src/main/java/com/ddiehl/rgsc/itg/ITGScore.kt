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
            Pair(FANTASTICS, ScoreElement(R.id.fantastics, R.string.fantastics, 0, 5, 5)),
            Pair(EXCELLENTS, ScoreElement(R.id.excellents, R.string.excellents, 0, 4, 5)),
            Pair(GREATS, ScoreElement(R.id.greats, R.string.greats, 0, 2, 5)),
            Pair(DECENTS, ScoreElement(R.id.decents, R.string.decents, 0, 0, 5)),
            Pair(WAY_OFFS, ScoreElement(R.id.wayoffs, R.string.wayoffs, 0, -6, 5)),
            Pair(MISSES, ScoreElement(R.id.misses, R.string.misses, 0, -12, 5)),
            Pair(HOLDS, ScoreElement(R.id.holds, R.string.holds, 1, 5, 0)),
            Pair(TOTAL_HOLDS, ScoreElement(R.id.total_holds, R.string.total_holds, 1, 0, 5)),
            Pair(MINES, ScoreElement(R.id.mines, R.string.mines, 1, -6, 0)),
            Pair(ROLLS, ScoreElement(R.id.rolls, R.string.rolls, 1, 5, 0)),
            Pair(TOTAL_ROLLS, ScoreElement(R.id.total_rolls, R.string.total_rolls, 1, 0, 5))
    )

    override val grade: String
        get() {
            val scorePercent = ((earned.toDouble() / potential.toDouble()) * 10000).toInt() / 100.00
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
