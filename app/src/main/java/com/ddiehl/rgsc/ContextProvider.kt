package com.ddiehl.rgsc

import android.content.Context

object ContextProvider {
    public lateinit var mContext: Context

    public fun set(context: Context) {
        mContext = context
    }

    public fun get(): Context = mContext
}