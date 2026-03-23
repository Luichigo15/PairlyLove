package app.luichigo15.pairly.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import app.luichigo15.pairly.common.PLPrefsConst
import app.luichigo15.pairly.domain.preferences.PLPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = PLPrefsConst.PREFS_NAME)

class PLPreferencesImpl(context: Context) : PLPreferences {

    private val dataStore = context.dataStore
    override val observePairCode: Flow<String>
        get() = dataStore.data.map { it[PLPrefsConst.PAIR_CODE_PREF] ?: "" }
    override val observeRole: Flow<String>
        get() = dataStore.data.map { it[PLPrefsConst.ROLE_PREF] ?: "" }
    override val observeShowNotifications: Flow<Boolean>
        get() = dataStore.data.map { it[PLPrefsConst.SHOW_NOTIFICATIONS_PREF] ?: false }
    override val observeShowGestureInfo: Flow<Boolean>
        get() = dataStore.data.map { it[PLPrefsConst.SHOW_GESTURE_INFO_PREF] ?: true }

    override suspend fun setPairCode(pairCode: String) {
        dataStore.edit { it[PLPrefsConst.PAIR_CODE_PREF] = pairCode }
    }

    override suspend fun setRole(role: String) {
        dataStore.edit { it[PLPrefsConst.ROLE_PREF] = role }
    }

    override suspend fun setShowNotifications(showNotifications: Boolean) {
        dataStore.edit { it[PLPrefsConst.SHOW_NOTIFICATIONS_PREF] = showNotifications }
    }

    override suspend fun setShowGestureInfo(showGestureInfo: Boolean) {
        dataStore.edit { it[PLPrefsConst.SHOW_GESTURE_INFO_PREF] = showGestureInfo }
    }
}