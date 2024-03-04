package com.osvaldo.newcheckoutarchpoc.data.model.repo

sealed class GenericResultRepository<T> {
    data class Success<T>(val data: T) : GenericResultRepository<T>()
    data class Error<T>(
        val errorMessage: String,
        val title: String = "",
        val module: String = "",
        val throwable: Throwable? = null,
    ) : GenericResultRepository<T>()
}
