package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.model.FishViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FishViewModel(
    private val sandwichUseCase: SandwichUseCase,
    private val completionUseCase: CompletionUseCase
) : BaseViewModel<FishViewModel.ViewIntent, FishViewModel.ViewState, FishViewModel.ViewEffect,
        SandwichDomainModel>() {

    private fun updateFishStatus(done: Boolean) = viewModelScope.launch {
        setState { copy(viewData = viewData.copy(isDone = done)) }
        completionUseCase.updateFishReady(done)
    }

    sealed class ViewIntent : BaseViewIntent {
        data class UpdateFishStatus(val isDone: Boolean) : ViewIntent()
        data class UpdateIsFromSea(val isFromSea: Boolean) : ViewIntent()
    }

    data class ViewState(
        val viewData: FishViewData = FishViewData()
    ) : BaseViewState

    sealed class ViewEffect : BaseViewEffect

    override fun useCaseModelFlow(): MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        sandwichUseCase.sandwich

    override fun initialState(): ViewState = ViewState()
    override fun useCaseError(error: Throwable?) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.ERROR
                )
            )
        }
    }

    override fun useCaseLoading() {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.LOADING
                )
            )
        }
    }

    override fun useCaseSuccess(useCaseModel: SandwichDomainModel) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.SUCCESS,
                    fish = useCaseModel.fish ?: "",
                    isGrilled = Factories.booleanFactory.random(),
                )
            )
        }
    }

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateFishStatus -> updateFishStatus(intent.isDone)
            is ViewIntent.UpdateIsFromSea -> {
                setState { copy(viewData = viewData.copy(isFromSea = intent.isFromSea)) }
            }
        }
    }
}