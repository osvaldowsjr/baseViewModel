package com.osvaldo.newcheckoutarchpoc.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.osvaldo.newcheckoutarchpoc.core.abstractions.BaseMviViewModel
import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import com.osvaldo.newcheckoutarchpoc.domain.useCase.SandwichUseCase
import com.osvaldo.newcheckoutarchpoc.presentation.model.BreadViewData
import com.osvaldo.newcheckoutarchpoc.presentation.model.ComponentState
import kotlinx.coroutines.launch

class BreadViewModel(
    private val sandwichUseCase: SandwichUseCase
) : BaseMviViewModel<BreadViewModel.ViewIntent, BreadViewModel.ViewState, BreadViewModel.ViewEffect>() {

    init {
        setSandwichCollector()
    }

    /**
     * This function is responsible for collecting the sandwich data from the use case
     * and updating the view state accordingly
     */
    private fun setSandwichCollector() = viewModelScope.launch {
        sandwichUseCase.sandwich.collect { allTheInfo ->
            splitTheSandwich(allTheInfo)
        }
    }

    /**
     * This function is responsible for updating the view state according to the sandwich data
     * And splitting the sandwich to the view (Using only the part needed)
     * @param sandwich: GenericResultFlow<SandwichDomainModel>
     *     The sandwich data
     *     It can be an error, loading or success
     * The ViewData is related only to the bread, gluten free and vegan
     */
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
                            bread = sandwich.data.bread ?: "",
                            isGlutenFree = Factories.booleanFactory.random(),
                            isVegan = Factories.booleanFactory.random()
                        )
                    )
                }
            }
        }
    }

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


    override fun initialState() = ViewState()

    sealed class ViewIntent : BaseViewIntent {
        data object UpdateBread : ViewIntent()
    }

    sealed class ViewEffect : BaseViewEffect

    data class ViewState(
        val viewData: BreadViewData = BreadViewData()
    ) : BaseViewState
}