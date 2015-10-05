package com.ddiehl.rgsc.itg

import com.ddiehl.rgsc.Score

data class ITGScore() : Score() {
    var fantastics: Int = 0
    var excellents: Int = 0
    var greats: Int = 0
    var decents: Int = 0
    var wayoffs: Int = 0
    var misses: Int = 0
    var holds: Int = 0
    var totalHolds: Int = 0
    var mines: Int = 0
    var rolls: Int = 0
    var totalRolls: Int = 0
}