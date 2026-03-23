package app.luichigo15.pairly.domain.preferences

import kotlinx.coroutines.flow.Flow

interface PLPreferences {

    val observePairCode: Flow<String>
    val observeRole: Flow<String>
    val observeShowNotifications: Flow<Boolean>
    val observeShowGestureInfo: Flow<Boolean>

    suspend fun setPairCode(pairCode: String)
    suspend fun setRole(role: String)
    suspend fun setShowNotifications(showNotifications: Boolean)
    suspend fun setShowGestureInfo(showGestureInfo: Boolean)
}