package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.Score
import java.text.DecimalFormat

public class ITGScore() : Score() {
    object ITGScore {
        const val FANTASTICS_WEIGHT = 5
        const val EXCELLENTS_WEIGHT = 4
        const val GREATS_WEIGHT = 2
        const val DECENTS_WEIGHT = 0
        const val WAYOFFS_WEIGHT = -6
        const val MISSES_WEIGHT = -12
        const val HOLDS_WEIGHT = 5
        const val MINES_WEIGHT = -6
        const val ROLLS_WEIGHT = 5
        const val BEST_WEIGHT = FANTASTICS_WEIGHT
    }
    
    var fantastics = 0
    var excellents = 0
    var greats = 0
    var decents = 0
    var wayoffs = 0
    var misses = 0
    var holds = 0
    var totalHolds = 0
    var mines = 0
    var rolls = 0
    var totalRolls = 0

    var earned =
            fantastics * ITGScore.FANTASTICS_WEIGHT +
            excellents * ITGScore.EXCELLENTS_WEIGHT +
            greats * ITGScore.GREATS_WEIGHT +
            decents * ITGScore.DECENTS_WEIGHT +
            wayoffs * ITGScore.WAYOFFS_WEIGHT +
            misses * ITGScore.MISSES_WEIGHT +
            holds * ITGScore.HOLDS_WEIGHT +
            mines * ITGScore.MINES_WEIGHT +
            rolls * ITGScore.ROLLS_WEIGHT
    
    var potential = 
            (fantastics + excellents + greats + decents + wayoffs + misses) * ITGScore.BEST_WEIGHT +
            totalHolds * ITGScore.HOLDS_WEIGHT +
            totalRolls * ITGScore.ROLLS_WEIGHT
    
    val stepTotal = fantastics + excellents + greats + decents + wayoffs + misses + 
            mines + holds + totalHolds + rolls + totalRolls
}