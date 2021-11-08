package com.glindor.fotogeopointer.utils

import android.util.Log

object Logger {
    private const val tag = "Glindor227"
    fun d (cl: Any? = null, text:String) {
        cl?.let {
            Log.d(tag, cl?.javaClass?.simpleName + ": " + text)
        } ?: let {
            Log.d(tag, text)
        }
    }
}