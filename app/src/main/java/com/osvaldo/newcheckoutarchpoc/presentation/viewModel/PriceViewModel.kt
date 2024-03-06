package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import kotlinx.coroutines.flow.MutableStateFlow

class PriceViewModel(
    private val sandwichUseCase: SandwichUseCase
) : BaseViewModel<PriceViewModel.ViewIntent, PriceViewModel.ViewState, PriceViewModel.ViewEffect,
        SandwichDomainModel>() {

    override fun useCaseModelFlow(): MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        sandwichUseCase.sandwich

    override fun initialState(): ViewState = ViewState()
    override fun useCaseError(error: Throwable?) {
        setState {
            copy(
                componentState = ComponentState.ERROR
            )
        }
    }

    override fun useCaseLoading() {
        setState {
            copy(
                componentState = ComponentState.LOADING
            )
        }
    }

    override fun useCaseSuccess(useCaseModel: SandwichDomainModel) {
        setState {
            copy(
                componentState = ComponentState.SUCCESS,
                price = useCaseModel.price.toString()
            )
        }
    }

    override fun intent(intent: ViewIntent) {
        when (intent) {
            else -> {
                // Do nothing
            }
        }
    }

    sealed class ViewIntent : BaseViewIntent
    data class ViewState(
        val price: String = "",
        val componentState: ComponentState = ComponentState.LOADING
    ) : BaseViewState

    sealed class ViewEffect : BaseViewEffect
}