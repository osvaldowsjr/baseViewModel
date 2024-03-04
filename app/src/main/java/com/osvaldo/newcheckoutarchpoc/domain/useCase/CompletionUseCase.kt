package com.osvaldo.newcheckoutarchpoc.domain.useCase

import com.osvaldo.newcheckoutarchpoc.core.CoroutineContextProvider
import com.osvaldo.newcheckoutarchpoc.domain.model.CompletionModel
import com.osvaldo.newcheckoutarchpoc.domain.model.GenericResultFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class CompletionUseCase(
    private val coroutineContextProvider: CoroutineContextProvider
) {

    /**
     * This stores the info about the completion of the sandwich
     * The business rules are stored here to know which component is ready to be completed
     */
    private var completionModel = CompletionModel(
        isCondimentReady = false,
        isMeatReady = false,
        isFishReady = false
    )

    /**
     * Flow being observed by the Presentation layer to get the completion information
     * The flow will always emit the current state of the completion
     * The Presentation layer should collect this flow and update the UI accordingly
     */
    val completion: MutableStateFlow<GenericResultFlow<CompletionModel>> =
        MutableStateFlow(GenericResultFlow.Success(completionModel))

    /**
     * Updates if the condiment is ready
     * This function is used to update the completion of the condiment for the sandwich
     */
    suspend fun updateCondimentReady(isDone: Boolean) = withContext(coroutineContextProvider.io) {
        completionModel = completionModel.copy(isCondimentReady = isDone)
        completion.emit(GenericResultFlow.Success(completionModel))
    }

    /**
     * Updates if the meat is ready
     * This function is used to update the completion of the meat for the sandwich
     */
    suspend fun updateMeatReady(isDone: Boolean) = withContext(coroutineContextProvider.io) {
        completionModel = completionModel.copy(isMeatReady = isDone)
        completion.emit(GenericResultFlow.Success(completionModel))
    }

    /**
     * Updates if the fish is ready
     * This function is used to update the completion of the fish for the sandwich
     */
    suspend fun updateFishReady(isDone: Boolean) = withContext(coroutineContextProvider.io) {
        completionModel = completionModel.copy(isFishReady = isDone)
        completion.emit(GenericResultFlow.Success(completionModel))
    }
}