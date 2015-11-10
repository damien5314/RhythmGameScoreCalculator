package com.ddiehl.rgsc.utils

import android.view.View
import android.view.ViewGroup
import java.util.*

fun ViewGroup.getChildren(): Iterable<View> {
    var list = ArrayList<View>()
    for (i in 0..childCount-1) list.add(getChildAt(i))
    return list
}