package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.model.CondimentViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CondimentsViewModel(
    private val sandwichUseCase: SandwichUseCase,
    private val completionUseCase: CompletionUseCase
) : BaseViewModel<CondimentsViewModel.ViewIntent, CondimentsViewModel.ViewState, CondimentsViewModel.ViewEffect,
        SandwichDomainModel>() {

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateCondimentStatus -> isCondimentDone(intent.isDone)
            ViewIntent.UpdateCondiments -> updateCondiments()
        }
    }

    /**
     * Ask the use case to update the condiments
     */
    private fun updateCondiments() = viewModelScope.launch {
        sandwichUseCase.updateCondiments()
    }

    /**
     * This function is needed to update the condiment status
     * Warn if the condiment is done or not
     * @param isDone: Boolean
     *    The condiment status
     */
    private fun isCondimentDone(isDone: Boolean) = viewModelScope.launch {
        setState { copy(viewData = viewData.copy(isDone = isDone)) }
        completionUseCase.updateCondimentReady(isDone)
    }

    override fun useCaseModelFlow(): MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        sandwichUseCase.sandwich


    override fun initialState() = ViewState()
    override fun useCaseError(error: Throwable?) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.ERROR,
                    isDone = false
                )
            )
        }
    }

    override fun useCaseLoading() {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.LOADING,
                    isDone = false
                )
            )
        }
    }

    override fun useCaseSuccess(useCaseModel: SandwichDomainModel) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.SUCCESS,
                    condiment = useCaseModel.condiments ?: "",
                )
            )
        }
    }

    sealed class ViewIntent : BaseViewIntent {
        data class UpdateCondimentStatus(val isDone: Boolean) : ViewIntent()
        data object UpdateCondiments : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect

    data class ViewState(
        val viewData: CondimentViewData = CondimentViewData()
    ) : BaseViewState
}