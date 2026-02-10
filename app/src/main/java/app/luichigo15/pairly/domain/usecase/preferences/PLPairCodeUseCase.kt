package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLPairCodeProvider
import javax.inject.Inject

class PLPairCodeUseCase @Inject constructor(
    private val preferences: PLPreferences,
    pairCodeProvider: PLPairCodeProvider
) {
    val observePairCode = pairCodeProvider.pairCode
    suspend operator fun invoke(pairCode: String) = preferences.setPairCode(pairCode)
}