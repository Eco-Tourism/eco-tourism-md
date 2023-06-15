package com.id.etourism.utils

sealed class ExceptionState<out T> {
    object Loading: ExceptionState<Nothing>()
    data class Success<out T>(val data: T): ExceptionState<T>()
    data class Failure(val error: String?): ExceptionState<Nothing>()
}