package app.luichigo15.pairly.ui.home.common.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.domain.usecase.preferences.PLPairCodeUseCase
import app.luichigo15.pairly.domain.usecase.preferences.PLShowNotificationsUseCase
import app.luichigo15.pairly.ui.home.common.home.model.PLHomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLHomeViewModel @Inject constructor(
    private val showNotificationsUseCase: PLShowNotificationsUseCase,
    codeUseCase: PLPairCodeUseCase
) : ViewModel() {

    val code = codeUseCase.observePairCode.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    fun onEvent(event: PLHomeEvent) {
        when (event) {
            is PLHomeEvent.RequestNotificationPermission -> {
                viewModelScope.launch {
                    showNotificationsUseCase(event.granted)
                }
            }
        }
    }

}