package app.luichigo15.pairly.domain.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class PLGift(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val expiresOn: Long = 0L,
    val redeemed: Boolean = false
) {

    fun checkValid() = name.isNotEmpty() && expiresOn != 0L

    fun checkExpired() = System.currentTimeMillis() > expiresOn || redeemed
}
