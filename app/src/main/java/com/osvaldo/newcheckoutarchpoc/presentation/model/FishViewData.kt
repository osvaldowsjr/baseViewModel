package com.osvaldo.newcheckoutarchpoc.presentation.model

data class FishViewData(
    val fish: String = "",
    val isGrilled: Boolean = false,
    val isFromSea: Boolean = false,
    val isDone: Boolean = false,
    val componentState: ComponentState = ComponentState.LOADING
)