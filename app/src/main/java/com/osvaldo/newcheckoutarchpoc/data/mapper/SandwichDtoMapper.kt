package com.osvaldo.newcheckoutarchpoc.data.mapper

import com.osvaldo.newcheckoutarchpoc.data.model.dto.SandwichDto
import com.osvaldo.newcheckoutarchpoc.data.model.dto.SandwichRequestDto
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichModel
import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichRequest

class SandwichDtoMapper {

    /**
     * Maps a SandwichDto to a SandwichModel, this is used to convert the response to the repository
     * @param sandwichDto
     * @return SandwichModel
     */
    fun responseToDomain(sandwichDto: SandwichDto): SandwichModel {
        return SandwichModel(
            bread = sandwichDto.bread,
            condiments = sandwichDto.condiments,
            fish = sandwichDto.fish,
            meat = sandwichDto.meat,
            id = sandwichDto.id,
            price = sandwichDto.price
        )
    }

    /**
     * Maps a SandwichRequest to a SandwichRequestDto, this is used to convert the request to the datasource
     * @param sandwichRequest
     * @return SandwichRequestDto
     */
    fun requestToDomain(sandwichRequest: SandwichRequest): SandwichRequestDto {
        return SandwichRequestDto(
            bread = sandwichRequest.bread,
            condiments = sandwichRequest.condiments,
            meat = sandwichRequest.meat,
            fish = sandwichRequest.fish,
        )
    }
}