package app.luichigo15.pairly.ui.role

import androidx.lifecycle.ViewModel
import app.luichigo15.pairly.ui.role.model.PLRoleData
import app.luichigo15.pairly.ui.role.model.PLRoleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PLRoleViewModel @Inject constructor(): ViewModel() {

    private val _roleData = MutableStateFlow(PLRoleData())
    val roleData = _roleData.asStateFlow()

    fun onRoleEvent(event: PLRoleEvent) {
        when (event) {
            is PLRoleEvent.RoleSelected -> _roleData.update { it.setRole(event.role) }
            PLRoleEvent.ClearRole -> _roleData.update { it.clear() }
        }
    }
}