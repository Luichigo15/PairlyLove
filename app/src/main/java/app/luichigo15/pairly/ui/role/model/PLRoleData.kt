package app.luichigo15.pairly.ui.role.model

import androidx.compose.runtime.Immutable
import app.luichigo15.pairly.common.PLRoleConst
import java.util.UUID

@Immutable
data class PLRoleData(
    val role: Int = 0,
    val roleCode: String = ""
) {
    val isValid: Boolean
        get() = role != 0 && roleCode.isNotEmpty()

    fun setRole(role: Int) = copy(
        role = role,
        roleCode = if (role == PLRoleConst.BOY_ROLE)
            UUID.randomUUID().toString().substring(0, 8)
        else ""
    )

    fun setCode(code: String) = copy(roleCode = code)

    fun clear() = PLRoleData()
}
