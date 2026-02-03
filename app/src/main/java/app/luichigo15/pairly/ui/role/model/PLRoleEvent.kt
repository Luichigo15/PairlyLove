package app.luichigo15.pairly.ui.role.model

sealed class PLRoleEvent {
    data class RoleSelected(val role: Int) : PLRoleEvent()
    data class CodeChanged(val code: String) : PLRoleEvent()
    data object ClearRole : PLRoleEvent()
}