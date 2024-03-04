package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseMviViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.model.FishViewData
import kotlinx.coroutines.launch

class FishViewModel(
    private val sandwichUseCase: SandwichUseCase,
    private val completionUseCase: CompletionUseCase
) : BaseMviViewModel<FishViewModel.ViewIntent, FishViewModel.ViewState, FishViewModel.ViewEffect>() {

    init {
        setSandwichCollector()
    }

    private fun setSandwichCollector() = viewModelScope.launch {
        sandwichUseCase.sandwich.collect { allTheInfo ->
            splitTheSandwich(allTheInfo)
        }
    }

    private fun splitTheSandwich(sandwich: GenericResultFlow<SandwichDomainModel>) {
        when (sandwich) {
            is GenericResultFlow.Error -> {
                setState {
                    copy(
                        viewData = viewData.copy(
                            componentState = ComponentState.ERROR
                        )
                    )
                }
            }

            is GenericResultFlow.Loading -> {
                setState {
                    copy(
                        viewData = viewData.copy(
                            componentState = ComponentState.LOADING
                        )
                    )
                }
            }

            is GenericResultFlow.Success -> {
                setState {
                    copy(
                        viewData = viewData.copy(
                            componentState = ComponentState.SUCCESS,
                            fish = sandwich.data.fish ?: "",
                            isGrilled = Factories.booleanFactory.random(),
                        )
                    )
                }
            }
        }
    }

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

    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateFishStatus -> updateFishStatus(intent.isDone)
            is ViewIntent.UpdateIsFromSea -> {
                setState { copy(viewData = viewData.copy(isFromSea = intent.isFromSea)) }
            }
        }
    }
}