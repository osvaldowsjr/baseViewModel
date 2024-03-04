package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseMviViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.CompletionModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import kotlinx.coroutines.launch

class CompletionViewModel(
    private val completionUseCase: CompletionUseCase
) : BaseMviViewModel<CompletionViewModel.ViewIntent, CompletionViewModel.ViewState, CompletionViewModel.ViewEffect>() {

    /**
     * This is the model that will be used to check if the condiment, meat and fish are ready
     * There is no need to expose it to the view
     * It is only used to check if the button should be enabled
     */
    private var completionModel: CompletionModel = CompletionModel(
        isCondimentReady = false, isMeatReady = false, isFishReady = false
    )

    init {
        setCompletionUseCaseCollector()
    }

    /**
     * This function is responsible for collecting the completion data from the use case
     * and updating the view state accordingly
     */
    private fun setCompletionUseCaseCollector() = viewModelScope.launch {
        completionUseCase.completion.collect {
            when (it) {
                is GenericResultFlow.Success -> {
                    updateButtonState(it.data)
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

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

    override fun initialState() = ViewState()

    override fun intent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.GoToNextScreen -> {
                goToNextScreen()
            }
        }
    }
}
