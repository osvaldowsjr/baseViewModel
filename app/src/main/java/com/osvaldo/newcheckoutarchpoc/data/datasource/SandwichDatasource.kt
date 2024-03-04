package com.osvaldo.newcheckoutarchpoc.data.datasource

import com.osvaldo.newcheckoutarchpoc.core.abstractions.Factories
import com.osvaldo.newcheckoutarchpoc.data.model.dto.SandwichDto
import com.osvaldo.newcheckoutarchpoc.data.model.dto.SandwichRequestDto
import kotlinx.coroutines.delay

/**
 * This class is equivalent to a provider, this is the point where we are going to fetch the info
 * Can be injected with our localDB or some kind of service, on this POC I'm going to
 * use only delays and stuff like that to simulate a request because the focus is going to be on
 * the architectural part not on making service requests
 */
class SandwichDatasource {

    /**
     * This function is going to simulate a request to get all the info
     * @return PocModelDto
     */
    suspend fun getAllTheInfo(requestDto: SandwichRequestDto): SandwichDto {
        try {
            delay(2000)
            return buildResponse(requestDto)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * This function is going to simulate a request to get all the info
     * It will return a SandwichDto with random values if the value is null
     */
    private fun buildResponse(requestDto: SandwichRequestDto): SandwichDto {
        return SandwichDto(
            bread = requestDto.bread ?: Factories.breadFactory.random(),
            condiments = requestDto.condiments ?: Factories.condimentsFactory.random(),
            meat = requestDto.meat ?: Factories.meatFactory.random(),
            fish = requestDto.fish ?: Factories.fishFactory.random(),
            id = randomInt(),
            price = randomDouble(),
        )
    }

    private fun randomInt() = (0..100).random()

    private fun randomDouble() = (0..100).random().toDouble()

}