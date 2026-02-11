package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLUserDataProvider
import javax.inject.Inject

class PLRoleUseCase @Inject constructor(
    private val preferences: PLPreferences,
    userDataProvider: PLUserDataProvider
) {
    val observeRole = userDataProvider.role
    suspend operator fun invoke(role: String) = preferences.setRole(role)
}