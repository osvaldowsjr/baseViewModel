package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.CompletionUseCase
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import com.osvaldo.newcheckoutarchpoc.presentation.model.MeatViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MeatViewModel(
    private val sandwichUseCase: SandwichUseCase,
    private val completionUseCase: CompletionUseCase
) : BaseViewModel<MeatViewModel.ViewIntent, MeatViewModel.ViewState, MeatViewModel.ViewEffect,
        SandwichDomainModel>() {

    init {
        setCollector()
    }

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateMeatStatus -> {
                updateMeatStatus(intent.isDone)
            }
        }
    }

    private fun updateMeatStatus(done: Boolean) = viewModelScope.launch {
        setState { copy(meatViewData = meatViewData.copy(isDone = done)) }
        completionUseCase.updateMeatReady(done)
    }

    override fun domainModelFlow(): MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        sandwichUseCase.sandwich

    override fun initialState(): ViewState = ViewState()
    override fun domainError(error: Throwable?) {
        setState {
            copy(
                meatViewData = meatViewData.copy(
                    componentState = ComponentState.ERROR
                )
            )
        }
    }

    override fun domainLoading() {
        setState {
            copy(
                meatViewData = meatViewData.copy(
                    componentState = ComponentState.LOADING
                )
            )
        }
    }

    override fun domainSuccess(domainModel: SandwichDomainModel) {
        setState {
            copy(
                meatViewData = meatViewData.copy(
                    componentState = ComponentState.SUCCESS,
                    meat = domainModel.meat ?: "",
                    isGrilled = Factories.booleanFactory.random()
                )
            )
        }
    }

    data class ViewState(
        val meatViewData: MeatViewData = MeatViewData(),
    ) : BaseViewState

    sealed class ViewIntent : BaseViewIntent {
        data class UpdateMeatStatus(val isDone: Boolean) : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect
}