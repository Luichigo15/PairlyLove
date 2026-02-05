package app.luichigo15.pairly.ui.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLUser
import app.luichigo15.pairly.domain.usecase.user.PLCreateUserUseCase
import app.luichigo15.pairly.ui.role.model.PLRoleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLRoleViewModel @Inject constructor(
    private val createUserUseCase: PLCreateUserUseCase
) : ViewModel() {

    private val _roleData = MutableStateFlow(PLUser())
    val roleData = _roleData.asStateFlow()

    private val _uiState = MutableStateFlow<L15Result<Boolean, PLErrorCodes>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    fun onRoleEvent(event: PLRoleEvent) {
        when (event) {
            is PLRoleEvent.RoleSelected -> _roleData.update { it.setRole(event.role) }
            PLRoleEvent.ClearRole -> _roleData.update { it.clear() }
            is PLRoleEvent.CodeChanged -> _roleData.update { it.setCode(event.code) }
            PLRoleEvent.Submit -> createRole()
            PLRoleEvent.ResetState -> _uiState.update { L15Result.Start }
        }
    }

    private fun createRole(){
        viewModelScope.launch {
            createUserUseCase(_roleData.value).collect { result ->
                when (result) {
                    is L15Result.Error -> _uiState.update { L15Result.Error(result.error) }
                    L15Result.Loading -> _uiState.update { L15Result.Loading }
                    L15Result.Start -> {}
                    is L15Result.Success -> _uiState.update { L15Result.Success(true) }
                }
            }
        }
    }
}