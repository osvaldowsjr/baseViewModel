package com.osvaldo.newcheckoutarchpoc.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineContextProvider {
    val io: CoroutineDispatcher
        get() = Dispatchers.IO

    class Default : CoroutineContextProvider
}