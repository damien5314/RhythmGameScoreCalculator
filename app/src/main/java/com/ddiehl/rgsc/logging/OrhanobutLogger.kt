package com.ddiehl.rgsc.logging

import com.ddiehl.rgsc.BuildConfig
import com.orhanobut.logger.LogLevel

object OrhanobutLogger : Logger {
    val TAG = "HTN"

    init {
        com.orhanobut.logger.Logger.init(TAG)
                .hideThreadInfo()
                .setMethodCount(0)
                .setLogLevel(if (BuildConfig.DEBUG) LogLevel.FULL else LogLevel.NONE)
    }

    override fun d(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.d(message, *args)
    }

    override fun e(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.e(message, *args)
    }

    override fun e(throwable: Throwable, message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.e(throwable, message, *args)
    }

    override fun w(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.w(message, *args)
    }

    override fun i(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.i(message, *args)
    }

    override fun v(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.v(message, *args)
    }

    override fun wtf(message: String, vararg args: Any) {
        com.orhanobut.logger.Logger.wtf(message, *args)
    }

    override fun json(json: String) {
        com.orhanobut.logger.Logger.json(json)
    }

    override fun xml(xml: String) {
        com.orhanobut.logger.Logger.xml(xml)
    }
}
