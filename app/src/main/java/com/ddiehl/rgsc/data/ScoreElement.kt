package com.ddiehl.rgsc.data

import android.support.annotation.IdRes
import android.support.annotation.StringRes

open class ScoreElement(
        @IdRes val id: Int, @StringRes val labelResId: Int, val column: Int,
        val weight: Int, val bestWeight: Int, val isStep: Boolean = true) {

    var count: Int = 0

}