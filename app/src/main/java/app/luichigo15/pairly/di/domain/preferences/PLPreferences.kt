package app.luichigo15.pairly.di.domain.preferences

import kotlinx.coroutines.flow.Flow

interface PLPreferences {

    val observePairCode: Flow<String>
    val observeShowNotifications: Flow<Boolean>

    suspend fun setPairCode(pairCode: String)
    suspend fun setShowNotifications(showNotifications: Boolean)
}