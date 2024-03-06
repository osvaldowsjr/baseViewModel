package com.osvaldo.newcheckoutarchpoc.core.abstractions

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        Intent : BaseViewModel.BaseViewIntent,
        State : BaseViewModel.BaseViewState,
        Effect : BaseViewModel.BaseViewEffect,
        UseCaseModel : Any
        > : ViewModel() {

    protected abstract fun useCaseModelFlow(): MutableStateFlow<GenericResultFlow<UseCaseModel>>
    protected abstract fun initialState(): State
    abstract fun intent(intent: Intent)

    private val initialState: State by lazy { initialState() }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val currentState: State
        get() = state.value
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _viewEffect = MutableSharedFlow<Effect>()
    val viewEffect = _viewEffect.asSharedFlow()

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _state.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _viewEffect.emit(effectValue) }
    }

    fun setUseCaseModelFlowCollector() = viewModelScope.launch {
        useCaseModelFlow().collect {
            when (it) {
                is GenericResultFlow.Error -> useCaseError()
                is GenericResultFlow.Loading -> useCaseLoading()
                is GenericResultFlow.Success -> useCaseSuccess(it.data)
            }
        }
    }

    abstract fun useCaseSuccess(useCaseModel: UseCaseModel)
    abstract fun useCaseError(error: Throwable? = null)
    abstract fun useCaseLoading()

    interface BaseViewState
    interface BaseViewEffect
    interface BaseViewIntent
}