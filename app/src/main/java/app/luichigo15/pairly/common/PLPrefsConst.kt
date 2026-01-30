package app.luichigo15.pairly.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PLPrefsConst {

    const val PREFS_NAME = "pairly_love_prefs"
    const val PAIR_CODE_KEY = "pair_code_key"
    val PAIR_CODE_PREF = stringPreferencesKey(PAIR_CODE_KEY)
    const val SHOW_NOTIFICATIONS_KEY = "show_notifications_key"
    val SHOW_NOTIFICATIONS_PREF = booleanPreferencesKey(SHOW_NOTIFICATIONS_KEY)
}