package com.github.maximpestryakov.spysdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity

internal class SpyActivityLifecycleCallbacks(
    private val logger: Logger
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        logger.log(TAG, "${activity::class.java} created")

        val layoutInflater = LayoutInflater.from(activity)
        layoutInflater.forceSetFactory2(SpyLayoutInflaterFactory(layoutInflater.factory2, logger))

        if (activity is FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            fragmentManager.registerFragmentLifecycleCallbacks(SpyFragmentLifecycleCallbacks(logger), true)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        logger.log(TAG, "${activity::class.java} started")
    }

    override fun onActivityResumed(activity: Activity) {
        logger.log(TAG, "${activity::class.java} resumed")
    }

    override fun onActivityPaused(activity: Activity) {
        logger.log(TAG, "${activity::class.java} paused")
    }

    override fun onActivityStopped(activity: Activity) {
        logger.log(TAG, "${activity::class.java} stopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logger.log(TAG, "${activity::class.java} save instance state")
    }

    override fun onActivityDestroyed(activity: Activity) {
        logger.log(TAG, "${activity::class.java} destroyed")
    }

    private companion object {
        const val TAG = "ACTIVITY"
    }
}
