package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.preferences.PLPreferences
import javax.inject.Inject


class PLShowNotificationsUseCase @Inject constructor(
    private val preferences: PLPreferences
) {
    val observeShowNotifications = preferences.observeShowNotifications
    suspend operator fun invoke(showNotifications: Boolean) =
        preferences.setShowNotifications(showNotifications)
}