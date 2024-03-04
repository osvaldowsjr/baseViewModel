package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseMviViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.model.MeatViewData
import kotlinx.coroutines.launch

class MeatViewModel(
    private val sandwichUseCase: SandwichUseCase,
    private val completionUseCase: CompletionUseCase
) : BaseMviViewModel<MeatViewModel.ViewIntent, MeatViewModel.ViewState, MeatViewModel.ViewEffect>() {

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
                        meatViewData = meatViewData.copy(
                            componentState = ComponentState.ERROR
                        )
                    )
                }
            }

            is GenericResultFlow.Loading -> {
                setState {
                    copy(
                        meatViewData = meatViewData.copy(
                            componentState = ComponentState.LOADING
                        )
                    )
                }
            }

            is GenericResultFlow.Success -> {
                setState {
                    copy(
                        meatViewData = meatViewData.copy(
                            componentState = ComponentState.SUCCESS,
                            meat = sandwich.data.meat ?: "",
                            isGrilled = Factories.booleanFactory.random()
                        )
                    )
                }
            }
        }
    }

    private fun updateMeatStatus(done: Boolean) = viewModelScope.launch {
        setState { copy(meatViewData = meatViewData.copy(isDone = done)) }
        completionUseCase.updateMeatReady(done)
    }

    sealed class ViewIntent : BaseViewIntent {
        data class UpdateMeatStatus(val isDone: Boolean) : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect {

    }

    data class ViewState(
        val meatViewData: MeatViewData = MeatViewData(),
    ) : BaseViewState

    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateMeatStatus -> {
                updateMeatStatus(intent.isDone)
            }
        }
    }
}