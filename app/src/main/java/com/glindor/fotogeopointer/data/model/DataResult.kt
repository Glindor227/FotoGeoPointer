package com.glindor.fotogeopointer.data.model

sealed class DataResult {
    data class Success<out T>(val data: T): DataResult()
    data class Error(val error: Throwable) : DataResult()
}