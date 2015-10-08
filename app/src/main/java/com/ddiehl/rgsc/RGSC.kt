package com.ddiehl.rgsc

import android.app.Application
import com.orhanobut.logger.Logger

public class RGSC : Application() {
    override fun onCreate() {
        Logger.init("RGSC")
                .setMethodCount(0)
                .hideThreadInfo()
    }
}