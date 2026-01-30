package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.di.domain.preferences.PLPreferences
import javax.inject.Inject

class PLPairCodeUseCase @Inject constructor(
    private val preferences: PLPreferences
) {
    val observePairCode = preferences.observePairCode
    suspend operator fun invoke(pairCode: String) = preferences.setPairCode(pairCode)
}