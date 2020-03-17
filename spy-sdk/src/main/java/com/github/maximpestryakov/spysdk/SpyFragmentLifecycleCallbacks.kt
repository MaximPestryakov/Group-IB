package com.github.maximpestryakov.spysdk

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

internal class SpyFragmentLifecycleCallbacks(
    private val logger: Logger
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) {
        logger.log(TAG, "${fragment::class.java} created")
    }

    override fun onFragmentAttached(fragmentManager: FragmentManager, fragment: Fragment, context: Context) {
        logger.log(TAG, "${fragment::class.java} attached")
    }

    override fun onFragmentStarted(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} started")
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} resumed")
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} paused")
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} stopped")
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} detached")
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, fragment: Fragment) {
        logger.log(TAG, "${fragment::class.java} destroyed")
    }

    private companion object {
        const val TAG = "FRAGMENT"
    }
}
