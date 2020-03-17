package com.github.maximpestryakov.spysdk

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi

internal class SpyLayoutInflaterFactory(
    private val factoryDelegate: LayoutInflater.Factory2?,
    private val logger: Logger
) : LayoutInflater.Factory2 {

    private val spyAttachStateChangeListener = object : View.OnAttachStateChangeListener {

        override fun onViewAttachedToWindow(view: View) {
            logger.log(TAG, "${view::class.java} attached with id = ${view.idName}, tag = ${view.tag}")
        }

        override fun onViewDetachedFromWindow(view: View) {
            logger.log(TAG, "${view::class.java} detached with id = ${view.idName}, tag = ${view.tag}")
        }
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        return factoryDelegate?.onCreateView(parent, name, context, attrs)?.also(::logView)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return factoryDelegate?.onCreateView(name, context, attrs)?.also(::logView)
    }

    private fun logView(view: View) {
        logger.log(TAG, "${view::class.java} instance created")
        view.addOnAttachStateChangeListener(spyAttachStateChangeListener)
        val originalTouchDelegate: TouchDelegate? = view.touchDelegate
        view.touchDelegate = object : TouchDelegate(Rect(), view) {

            override fun onTouchEvent(event: MotionEvent): Boolean {
                logger.log(TAG, "${view::class.java} ${event.actionToString()} id = ${view.idName}, tag = ${view.tag}")
                return originalTouchDelegate?.onTouchEvent(event) ?: false
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTouchExplorationHoverEvent(event: MotionEvent): Boolean {
                return originalTouchDelegate?.onTouchExplorationHoverEvent(event) ?: false
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun getTouchDelegateInfo(): AccessibilityNodeInfo.TouchDelegateInfo {
                return originalTouchDelegate?.touchDelegateInfo ?: super.getTouchDelegateInfo()
            }
        }
    }

    private companion object {
        const val TAG = "VIEW"
    }
}
