package com.osvaldo.newcheckoutarchpoc.domain.useCase

import com.osvaldo.newcheckoutarchpoc.core.CoroutineContextProvider
import com.osvaldo.newcheckoutarchpoc.core.abstractions.GenericResultFlow
import com.osvaldo.newcheckoutarchpoc.data.model.repo.GenericResultRepository
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichModel
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichRequest
import com.osvaldo.newcheckoutarchpoc.data.repository.SandwichRepository
import com.osvaldo.newcheckoutarchpoc.domain.mapper.SandwichModelMapper
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SandwichUseCase(
    private val sandwichRepository: SandwichRepository,
    private val sandwichModelMapper: SandwichModelMapper,
    private val coroutineContextProvider: CoroutineContextProvider
) {

    /**
     * Sandwich information
     * Flow being observed by the Presentation layer to get the sandwich information
     * This flow will emit the following states:
     * - Loading: when the use case is fetching the sandwich information
     * - Success: when the use case successfully fetched the sandwich information
     * - Error: when the use case failed to fetch the sandwich information
     */
    val sandwich: MutableStateFlow<GenericResultFlow<SandwichDomainModel>> =
        MutableStateFlow(GenericResultFlow.Loading())

    private var sandwichRequest = SandwichRequest()

    /**
     * Fetches the sandwich information whenever this useCase is started
     * This is done in a coroutine to avoid blocking the main thread
     */
    init {
        CoroutineScope(coroutineContextProvider.io).launch {
            getSandwichInfo()
        }
    }

    /**
     * Fetches the sandwich information
     * This is done in a coroutine to avoid blocking the main thread
     * This function makes the value to be updated, so there is no need to return anything
     * The function is private because there is no need to expose it to the Presentation layer
     * The Presentation layer should only observe the sandwich flow
     */
    private suspend fun getSandwichInfo() {
        sandwich.emit(GenericResultFlow.Loading())

        when (val result = sandwichRepository.buildASandwich(sandwichRequest)) {
            is GenericResultRepository.Error -> {
                sandwich.emit(
                    GenericResultFlow.Error(
                        result.errorMessage
                    )
                )
            }

            is GenericResultRepository.Success -> {
                updateRequestData(result.data)
                sandwich.emit(
                    GenericResultFlow.Success(
                        sandwichModelMapper.toDomain(result.data)
                    )
                )
            }
        }
    }

    private fun updateRequestData(result: SandwichModel) {
        sandwichRequest = sandwichRequest.copy(
            bread = result.bread,
            condiments = result.condiments,
            meat = result.meat,
            fish = result.fish
        )
    }

    /**
     * Update the bread, it makes the request to the repository to update the bread
     * This is done in a coroutine to avoid blocking the main thread
     * This function makes the value to be updated, so there is no need to return anything
     * The function is exposed to the Presentation layer to be called when the bread is updated
     * So for the Presentation layer, there is no need to know how or where the bread is updated
     */
    suspend fun updateBread() = withContext(coroutineContextProvider.io) {
        sandwichRequest = sandwichRequest.copy(bread = null)
        getSandwichInfo()
    }

    /**
     * Update the meat, it makes the request to the repository to update the meat
     * This is done in a coroutine to avoid blocking the main thread
     * This function makes the value to be updated, so there is no need to return anything
     * The function is exposed to the Presentation layer to be called when the meat is updated
     * So for the Presentation layer, there is no need to know how or where the meat is updated
     */
    suspend fun updateCondiments() = withContext(coroutineContextProvider.io) {
        sandwichRequest = sandwichRequest.copy(condiments = null)
        getSandwichInfo()
    }
}
