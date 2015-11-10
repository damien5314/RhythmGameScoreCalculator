package com.ddiehl.rgsc

import android.content.Context

object ContextProvider {
    private lateinit var _context: Context

    public fun set(context: Context) {
        _context = context
    }

    public fun get(): Context = _context
}