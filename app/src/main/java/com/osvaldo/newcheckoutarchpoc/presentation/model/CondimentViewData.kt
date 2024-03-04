package com.osvaldo.newcheckoutarchpoc.presentation.model

data class CondimentViewData(
    val condiment: String = "",
    val isDone: Boolean = false,
    val componentState: ComponentState = ComponentState.LOADING
)
