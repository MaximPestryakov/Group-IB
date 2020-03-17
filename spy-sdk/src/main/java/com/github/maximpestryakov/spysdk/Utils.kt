package com.github.maximpestryakov.spysdk

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View

private val mFactory2Field by lazy(LazyThreadSafetyMode.NONE) {
    try {
        LayoutInflater::class.java.getDeclaredField("mFactory2").apply { isAccessible = true }
    } catch (e: NoSuchFieldException) {
        null
    }
}

internal fun LayoutInflater.forceSetFactory2(factory: LayoutInflater.Factory2) {
    try {
        mFactory2Field?.set(this, factory)
    } catch (e: IllegalAccessException) {
    }
}

internal val View.idName: String?
    get() {
        return try {
            resources.getResourceName(id)
        } catch (e: Resources.NotFoundException) {
            null
        }
    }

internal fun MotionEvent.actionToString(): String {
    return when (action) {
        MotionEvent.ACTION_DOWN -> "ACTION_DOWN"
        MotionEvent.ACTION_UP -> "ACTION_UP"
        MotionEvent.ACTION_CANCEL -> "ACTION_CANCEL"
        MotionEvent.ACTION_OUTSIDE -> "ACTION_OUTSIDE"
        MotionEvent.ACTION_MOVE -> "ACTION_MOVE"
        MotionEvent.ACTION_HOVER_MOVE -> "ACTION_HOVER_MOVE"
        MotionEvent.ACTION_SCROLL -> "ACTION_SCROLL"
        MotionEvent.ACTION_HOVER_ENTER -> "ACTION_HOVER_ENTER"
        MotionEvent.ACTION_HOVER_EXIT -> "ACTION_HOVER_EXIT"
        MotionEvent.ACTION_BUTTON_PRESS -> "ACTION_BUTTON_PRESS"
        MotionEvent.ACTION_BUTTON_RELEASE -> "ACTION_BUTTON_RELEASE"
        else -> {
            val index = action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN -> "ACTION_POINTER_DOWN($index)"
                MotionEvent.ACTION_POINTER_UP -> "ACTION_POINTER_UP($index)"
                else -> action.toString()
            }
        }
    }
}
