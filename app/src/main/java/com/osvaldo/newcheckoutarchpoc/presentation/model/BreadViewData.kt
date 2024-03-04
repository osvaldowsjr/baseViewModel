package com.osvaldo.newcheckoutarchpoc.presentation.model


data class BreadViewData(
    val bread: String = "",
    val isGlutenFree: Boolean = false,
    val isVegan: Boolean = false,
    val componentState: ComponentState = ComponentState.LOADING
)
