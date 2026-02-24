package app.luichigo15.pairly.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PLUser(
    val uuid: String = "",
    val role: String = "",
    val notificationsToken: String = "",
) {

    fun checkValid() = role.isNotEmpty() && uuid.isNotEmpty()
}
