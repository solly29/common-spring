package com.solly.commonspring.util

import org.slf4j.ILoggerFactory
import org.slf4j.LoggerFactory
import kotlin.jvm.java
import kotlin.toString

class Log {
    companion object {
        val logger: ILoggerFactory by lazy { LoggerFactory.getILoggerFactory() }
    }
}

inline fun <reified T> T.infoLog(str: Any?) {
    Log.logger.getLogger(T::class.java.toString()).info(str.toString())
}

inline fun <reified T> T.errorLog(str: Any?) {
    Log.logger.getLogger(T::class.java.toString()).error(str.toString())
}

inline fun <reified T> T.debugLog(str: Any?) {
    Log.logger.getLogger(T::class.java.toString()).debug(str.toString())
}
