package com.osvaldo.newcheckoutarchpoc.core.abstractions

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified ViewModelType : ViewModel> koinBaseViewModel(): ViewModelType {
    val viewModel = koinViewModel<ViewModelType>()
    if (viewModel is BaseViewModel<*, *, *, *>) {
        viewModel.setCollector()
    }
    return viewModel
}