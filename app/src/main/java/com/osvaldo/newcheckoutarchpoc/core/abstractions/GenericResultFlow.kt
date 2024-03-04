package com.osvaldo.newcheckoutarchpoc.core.abstractions

sealed class GenericResultFlow<T> {
    data class Success<T>(val data: T) : GenericResultFlow<T>()
    data class Error<T>(val errorMessage: String) : GenericResultFlow<T>()
    data class Loading<T>(val info: String = "") : GenericResultFlow<T>()
}