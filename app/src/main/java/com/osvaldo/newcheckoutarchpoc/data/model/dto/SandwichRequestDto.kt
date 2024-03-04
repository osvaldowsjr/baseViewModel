package com.osvaldo.newcheckoutarchpoc.data.model.dto

data class SandwichRequestDto(
    val bread: String? = "Flat bread",
    val condiments: String? = null,
    val meat: String? = null,
    val fish: String? = null,
)