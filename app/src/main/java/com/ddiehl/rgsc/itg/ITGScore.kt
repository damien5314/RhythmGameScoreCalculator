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
            Pair(FANTASTICS, ScoreElement(R.id.itg_fantastics, R.string.itg_fantastics, 0, 5, 5)),
            Pair(EXCELLENTS, ScoreElement(R.id.itg_excellents, R.string.itg_excellents, 0, 4, 5)),
            Pair(GREATS, ScoreElement(R.id.itg_greats, R.string.itg_greats, 0, 2, 5)),
            Pair(DECENTS, ScoreElement(R.id.itg_decents, R.string.itg_decents, 0, 0, 5)),
            Pair(WAY_OFFS, ScoreElement(R.id.itg_wayoffs, R.string.itg_wayoffs, 0, -6, 5)),
            Pair(MISSES, ScoreElement(R.id.itg_misses, R.string.itg_misses, 0, -12, 5)),
            Pair(HOLDS, ScoreElement(R.id.itg_holds, R.string.itg_holds, 1, 5, 0)),
            Pair(TOTAL_HOLDS, ScoreElement(R.id.itg_total_holds, R.string.itg_total_holds, 1, 0, 5)),
            Pair(MINES, ScoreElement(R.id.itg_mines, R.string.itg_mines, 1, -6, 0)),
            Pair(ROLLS, ScoreElement(R.id.itg_rolls, R.string.itg_rolls, 1, 5, 0)),
            Pair(TOTAL_ROLLS, ScoreElement(R.id.itg_total_rolls, R.string.itg_total_rolls, 1, 0, 5))
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
