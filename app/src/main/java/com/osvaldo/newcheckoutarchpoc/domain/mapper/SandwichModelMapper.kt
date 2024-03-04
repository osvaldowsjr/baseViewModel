package com.osvaldo.newcheckoutarchpoc.domain.mapper

import com.osvaldo.newcheckoutarchpoc.data.model.repo.SandwichModel
import com.osvaldo.newcheckoutarchpoc.domain.model.SandwichDomainModel

class SandwichModelMapper {

    fun toDomain(sandwichModel: SandwichModel): SandwichDomainModel {
        return SandwichDomainModel(
            bread = sandwichModel.bread,
            condiments = sandwichModel.condiments,
            meat = sandwichModel.meat,
            fish = sandwichModel.fish,
            price = sandwichModel.price
        )
    }
}