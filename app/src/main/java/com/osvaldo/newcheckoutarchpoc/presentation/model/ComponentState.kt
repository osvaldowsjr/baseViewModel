package com.osvaldo.newcheckoutarchpoc.presentation.model

sealed class ComponentState {
    data object LOADING : ComponentState()
    data object SUCCESS : ComponentState()
    data object ERROR : ComponentState()
}
