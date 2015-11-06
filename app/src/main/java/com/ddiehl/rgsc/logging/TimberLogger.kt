package com.ddiehl.rgsc.logging

import timber.log.Timber

object TimberLogger : Logger {
    init {
        val tree = Timber.DebugTree()
        Timber.plant(tree)
    }

    override fun d(message: String, vararg args: Any) {
        Timber.d(message, *args)
    }

    override fun e(message: String, vararg args: Any) {
        Timber.e(message, *args)
    }

    override fun e(throwable: Throwable, message: String, vararg args: Any) {
        Timber.e(throwable, message, *args)
    }

    override fun w(message: String, vararg args: Any) {
        Timber.w(message, *args)
    }

    override fun i(message: String, vararg args: Any) {
        Timber.i(message, *args)
    }

    override fun v(message: String, vararg args: Any) {
        Timber.v(message, *args)
    }

    override fun wtf(message: String, vararg args: Any) {
        Timber.wtf(message, *args)
    }

    override fun json(json: String) {
        // Uses debug method by default, since Timber does not have json pretty-printing
        Timber.d(json)
    }

    override fun xml(xml: String) {
        // Uses debug method by default, since Timber does not have xml pretty-printing
        Timber.d(xml)
    }
}
