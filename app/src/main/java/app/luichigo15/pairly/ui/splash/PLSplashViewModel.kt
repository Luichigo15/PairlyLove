package app.luichigo15.pairly.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.domain.usecase.preferences.PLPairCodeUseCase
import app.luichigo15.pairly.domain.usecase.preferences.PLRoleUseCase
import app.luichigo15.pairly.ui.splash.model.PLSplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PLSplashViewModel @Inject constructor(
    private val roleUseCase: PLRoleUseCase,
    private val pairCodeUseCase: PLPairCodeUseCase
) : ViewModel() {

    private val _splashState = MutableStateFlow(PLSplashState())
    val splashState = _splashState.asStateFlow()

    init {
        viewModelScope.launch {
            roleUseCase.observeRole.collect { role ->
                _splashState.update { it.copy(role = role) }
            }

            pairCodeUseCase.observePairCode.collect { pairCode ->
                _splashState.update { it.copy(pairCode = pairCode) }
            }
        }
    }

    fun onAnimationFinished() {
        _splashState.update { it.copy(isAnimationFinished = true) }
    }
}