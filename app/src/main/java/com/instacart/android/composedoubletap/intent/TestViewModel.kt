package com.instacart.android.composedoubletap.intent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun onUIEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UIEvent.SwitchToggle -> {
                    _viewState.value = _viewState.value.copy(isToggled = uiEvent.isToggled)
                }
            }
        }
    }
}

data class ViewState(val isToggled: Boolean = false)

sealed interface UIEvent {
    data class SwitchToggle(val isToggled: Boolean) : UIEvent
}