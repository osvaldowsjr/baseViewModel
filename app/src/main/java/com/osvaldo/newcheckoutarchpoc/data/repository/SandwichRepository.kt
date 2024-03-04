package com.osvaldo.newcheckoutarchpoc.data.repository

import com.osvaldo.newcheckoutarchpoc.core.CoroutineContextProvider
import com.osvaldo.newcheckoutarchpoc.data.datasource.SandwichDatasource
import com.osvaldo.newcheckoutarchpoc.data.mapper.SandwichDtoMapper
import com.osvaldo.newcheckoutarchpoc.data.model.repo.GenericResultRepository
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichModel
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichRequest
import kotlinx.coroutines.withContext

/**
 * This repository is responsible for fetching the data from the data source and returning it to the UseCase
 * It also handles the coroutine context
 * @param dataSource the data source
 * @param coroutineContext the coroutine context
 * */
class SandwichRepository(
    private val dataSource: SandwichDatasource,
    private val coroutineContext: CoroutineContextProvider,
    private val sandwichDtoMapper: SandwichDtoMapper
) {
    /**
     * This function fetches all the info from the data source
     * @return SandwichRequest
     * */
    private var currentSandwichRequest: SandwichRequest = SandwichRequest()

    /**
     * Interacts with the data source to get all the info
     * This updates the currentSandwichRequest
     * This changes the coroutine context to IO
     * @param sandwichRequest the request
     * @return GenericResultRepository<SandwichModel>
     */
    suspend fun buildASandwich(
        sandwichRequest: SandwichRequest
    ): GenericResultRepository<SandwichModel> =
        withContext(context = coroutineContext.io) {
            try {
                currentSandwichRequest = sandwichRequest
                GenericResultRepository.Success(
                    sandwichDtoMapper.responseToDomain(
                        dataSource.getAllTheInfo(
                            sandwichDtoMapper.requestToDomain(sandwichRequest)
                        )
                    )
                )
            } catch (e: Exception) {
                GenericResultRepository.Error(
                    e.message ?: "An error occurred"
                )
            }
        }
}