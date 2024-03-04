package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseMviViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import kotlinx.coroutines.launch

class PriceViewModel(
    private val sandwichUseCase: SandwichUseCase
) : BaseMviViewModel<PriceViewModel.ViewIntent, PriceViewModel.ViewState, PriceViewModel.ViewEffect>() {
    sealed class ViewIntent : BaseViewIntent
    data class ViewState(
        val price: String = "",
        val componentState: ComponentState = ComponentState.LOADING
    ) : BaseViewState

    init {
        setSandwichCollector()
    }

    private fun setSandwichCollector() = viewModelScope.launch {
        sandwichUseCase.sandwich.collect {
            when (it) {
                is GenericResultFlow.Error -> {
                    setState {
                        copy(
                            componentState = ComponentState.ERROR
                        )
                    }
                }

                is GenericResultFlow.Loading -> {
                    setState {
                        copy(
                            componentState = ComponentState.LOADING
                        )
                    }
                }

                is GenericResultFlow.Success -> {
                    setState {
                        copy(
                            componentState = ComponentState.SUCCESS,
                            price = it.data.price.toString()
                        )
                    }
                }
            }
        }
    }

    sealed class ViewEffect : BaseViewEffect

    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        when (intent) {
            else -> {
                // Do nothing
            }
        }
    }
}