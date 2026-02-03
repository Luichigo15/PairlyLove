package app.luichigo15.pairly.ui.role

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.ui.role.model.PLRoleData
import app.luichigo15.pairly.ui.role.model.PLRoleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLRoleViewModel @Inject constructor(): ViewModel() {

    private val _roleData = MutableStateFlow(PLRoleData())
    val roleData = _roleData.asStateFlow()

    private val _uiState = MutableStateFlow<L15Result<Boolean,Unit>>(L15Result.Start)
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
            _uiState.update { L15Result.Loading }
            delay(3000)
            _uiState.update { L15Result.Error(Unit) }
            delay(3000)
            _uiState.update { L15Result.Success(true) }
        }
    }
}