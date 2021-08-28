package com.vitgames.passworkd_gen.utils

import android.util.Log
import javax.inject.Inject

interface Logger {
    fun printMessage(message: String)
    fun printError(error: String)
}

class LoggerImpl @Inject constructor() : Logger {

    override fun printMessage(message: String) {
        Log.d(APP_TAG_MESSAGE, message)
    }

    override fun printError(error: String) {
        Log.d(APP_TAG_ERROR, error)
    }
}

const val APP_TAG_MESSAGE = "pass_MSG"
const val APP_TAG_ERROR = "pass_ERROR"