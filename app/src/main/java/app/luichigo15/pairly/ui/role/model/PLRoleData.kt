package app.luichigo15.pairly.ui.role.model

import app.luichigo15.pairly.common.PLRoleConst
import java.util.UUID

data class PLRoleData(
    val role: Int = 0,
    val roleCode: String = ""
) {
    fun setRole(role: Int) = copy(
        role = role,
        roleCode = if (role == PLRoleConst.BOY_ROLE)
            UUID.randomUUID().toString().substring(0, 8)
        else ""
    )

    fun clear() = PLRoleData()
}
