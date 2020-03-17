package com.github.maximpestryakov.spysdk

import android.os.Handler
import android.util.Log

internal class SpyLogger(
    private val handler: Handler
) : Logger {

    override fun log(tag: String, message: String) {
        handler.post { Log.d("SpySdk", "$tag: $message") }
    }
}
