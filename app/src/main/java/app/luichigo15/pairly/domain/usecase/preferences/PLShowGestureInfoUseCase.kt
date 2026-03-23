package app.luichigo15.pairly.domain.usecase.preferences

import app.luichigo15.pairly.domain.preferences.PLPreferences
import javax.inject.Inject

class PLShowGestureInfoUseCase @Inject constructor(
    private val preferences: PLPreferences
) {
    val observeShowGestureInfo = preferences.observeShowGestureInfo
    suspend operator fun invoke(showGestureInfo: Boolean) =
        preferences.setShowGestureInfo(showGestureInfo)
}