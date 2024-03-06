package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.CompletionModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class CompletionViewModel(
    private val completionUseCase: CompletionUseCase
) : BaseViewModel<
        CompletionViewModel.ViewIntent,
        CompletionViewModel.ViewState,
        CompletionViewModel.ViewEffect,
        CompletionModel
        >() {

    /**
     * This is the model that will be used to check if the condiment, meat and fish are ready
     * There is no need to expose it to the view
     * It is only used to check if the button should be enabled
     */
    private var completionModel: CompletionModel = CompletionModel(
        isCondimentReady = false, isMeatReady = false, isFishReady = false
    )

    /**
     * This function is responsible for updating the view state according to the completion data
     * And updating the button state
     * @param it: CompletionModel
     *     The completion data
     * It parses the information to the view state and apply all the necessary logic!
     */
    private fun updateButtonState(it: CompletionModel) {
        completionModel = it
        setState { copy(buttonEnabled = it.isCondimentReady && it.isMeatReady) }
    }

    /**
     * This function is responsible for checking if the fish is ready
     * And if it is, it will go to the next screen
     * If it is not, it will show an error
     */
    private fun goToNextScreen() {
        if (completionModel.isFishReady) {
            setEffect { ViewEffect.GoToNextScreen }
        } else {
            setEffect { ViewEffect.ShowError }
        }
    }

    sealed class ViewIntent : BaseViewIntent {
        data object GoToNextScreen : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect {
        data object GoToNextScreen : ViewEffect()
        data object ShowError : ViewEffect()
    }

    data class ViewState(
        val buttonEnabled: Boolean = false
    ) : BaseViewState

    override fun useCaseModelFlow(): MutableStateFlow<GenericResultFlow<CompletionModel>> =
        completionUseCase.completion

    override fun initialState() = ViewState()
    override fun useCaseError(error: Throwable?) {
        // Do nothing
    }

    override fun useCaseLoading() {
        // Do nothing
    }

    override fun useCaseSuccess(useCaseModel: CompletionModel) {
        updateButtonState(useCaseModel)
    }

    override fun intent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.GoToNextScreen -> {
                goToNextScreen()
            }
        }
    }
}
