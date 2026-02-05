package app.luichigo15.pairly.domain.model

import androidx.compose.runtime.Immutable
import app.luichigo15.pairly.common.PLRoleConst
import java.util.UUID

@Immutable
data class PLUser(
    val uuid: String = "",
    val role: String = "",
    val notificationsToken: String = "",
) {
    val isValid: Boolean
        get() = role.isNotEmpty() && uuid.isNotEmpty()

    fun setRole(role: String) = copy(
        role = role,
        uuid = if (role == PLRoleConst.BOY_ROLE)
            UUID.randomUUID().toString().substring(0, 8)
        else ""
    )

    fun setCode(code: String) = copy(uuid = code)

    fun clear() = PLUser()
}
