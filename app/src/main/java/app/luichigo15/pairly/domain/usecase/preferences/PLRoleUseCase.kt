package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.domain.preferences.PLPreferences
import javax.inject.Inject

class PLRoleUseCase @Inject constructor(
    private val preferences: PLPreferences
) {
    val observeRole = preferences.observeRole
    suspend operator fun invoke(role: String) = preferences.setRole(role)
}