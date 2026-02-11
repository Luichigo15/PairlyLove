package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLUserDataProvider
import javax.inject.Inject

class PLPairCodeUseCase @Inject constructor(
    private val preferences: PLPreferences,
    userDataProvider: PLUserDataProvider
) {
    val observePairCode = userDataProvider.pairCode
    suspend operator fun invoke(pairCode: String) = preferences.setPairCode(pairCode)
}