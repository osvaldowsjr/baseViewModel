package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.BreadViewData
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BreadViewModel(
    private val sandwichUseCase: SandwichUseCase
) : BaseViewModel<BreadViewModel.ViewIntent, BreadViewModel.ViewState, BreadViewModel.ViewEffect,
        SandwichDomainModel>() {

    private fun updateBread() = viewModelScope.launch {
        sandwichUseCase.updateBread()
    }

    override fun intent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.UpdateBread -> {
                updateBread()
            }
        }
    }

    override fun domainModelFlow(): MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        sandwichUseCase.sandwich


    override fun initialState() = ViewState()
    override fun domainError(error: Throwable?) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.ERROR
                )
            )
        }
    }

    override fun domainLoading() {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.LOADING
                )
            )
        }
    }

    override fun domainSuccess(domainModel: SandwichDomainModel) {
        setState {
            copy(
                viewData = viewData.copy(
                    componentState = ComponentState.SUCCESS,
                    bread = domainModel.bread ?: "",
                    isGlutenFree = Factories.booleanFactory.random(),
                    isVegan = Factories.booleanFactory.random()
                )
            )
        }
    }

    sealed class ViewIntent : BaseViewIntent {
        data object UpdateBread : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect

    data class ViewState(
        val viewData: BreadViewData = BreadViewData()
    ) : BaseViewState
}