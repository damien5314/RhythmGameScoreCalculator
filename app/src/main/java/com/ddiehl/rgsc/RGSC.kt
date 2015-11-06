package com.ddiehl.rgsc

import android.app.Application
import com.ddiehl.rgsc.logging.Logger
import com.ddiehl.rgsc.logging.TimberLogger

public class RGSC : Application() {
    override fun onCreate() {
        ContextProvider.set(this)
    }

    companion object {
        public fun getLogger(): Logger {
            return TimberLogger
        }
    }
}