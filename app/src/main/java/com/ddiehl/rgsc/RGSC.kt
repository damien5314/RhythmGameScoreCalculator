package com.ddiehl.rgsc

import android.app.Application
import com.ddiehl.rgsc.data.ITGStorage
import com.orhanobut.logger.Logger

public class RGSC : Application() {
    override fun onCreate() {
        Logger.init("RGSC")
                .hideThreadInfo()
        ContextProvider.set(this)
    }
}