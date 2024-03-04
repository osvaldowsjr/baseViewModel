package com.osvaldo.newcheckoutarchpoc.presentation.model


data class MeatViewData(
    val meat: String = "",
    val isGrilled: Boolean = false,
    val componentState: ComponentState = ComponentState.LOADING,
    val isDone: Boolean = false
)
