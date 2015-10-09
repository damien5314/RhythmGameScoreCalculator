package com.ddiehl.rgsc

import android.app.Application
import com.ddiehl.rgsc.data.AndroidStorage
import com.orhanobut.logger.Logger

public class RGSC : Application() {
    override fun onCreate() {
        Logger.init("RGSC")
                .hideThreadInfo()
        AndroidStorage.init(this)
    }
}