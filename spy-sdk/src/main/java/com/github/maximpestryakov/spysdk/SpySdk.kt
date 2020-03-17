package com.github.maximpestryakov.spysdk

import android.app.Application
import android.os.Handler
import android.os.HandlerThread

internal object SpySdk {

    fun init(application: Application) {
        val handlerThread = HandlerThread("Spy Handler Thread")
        handlerThread.start()
        val spyLogger = SpyLogger(Handler(handlerThread.looper))
        application.registerActivityLifecycleCallbacks(SpyActivityLifecycleCallbacks(spyLogger))
    }
}
