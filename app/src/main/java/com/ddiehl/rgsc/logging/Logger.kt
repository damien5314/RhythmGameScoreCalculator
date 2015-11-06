package com.ddiehl.rgsc.logging

interface Logger {
    fun d(message: String, vararg args: Any)
    fun e(message: String, vararg args: Any)
    fun e(throwable: Throwable, message: String, vararg args: Any)
    fun w(message: String, vararg args: Any)
    fun i(message: String, vararg args: Any)
    fun v(message: String, vararg args: Any)
    fun wtf(message: String, vararg args: Any)
    fun json(json: String)
    fun xml(xml: String)
}
