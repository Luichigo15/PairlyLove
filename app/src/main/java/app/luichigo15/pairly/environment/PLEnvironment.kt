package app.luichigo15.pairly.environment

import android.content.Context
import app.luichigo15.common.environment.L15Environment
import app.luichigo15.common.environment.L15EnvironmentKey
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

private const val DEV_ENV = "DEV"

object PLEnvironment : L15Environment() {

    init {
        System.loadLibrary("pl-firebase-lib")
        System.loadLibrary("pl-environment-lib")
    }

    private external fun getFirebaseConfig(): String

    private fun getFirebaseOptions(): FirebaseOptions {
        val firebaseConfig = createFirebaseConfig(getFirebaseConfig())
        return FirebaseOptions.Builder()
            .setApiKey(firebaseConfig.apiKey)
            .setDatabaseUrl(firebaseConfig.databaseUrl)
            .setProjectId(firebaseConfig.projectId)
            .setStorageBucket(firebaseConfig.storageBucket)
            .setGcmSenderId(firebaseConfig.senderId)
            .setApplicationId(firebaseConfig.appId)
            .build()
    }

    private fun firebaseAppExists(context: Context): Boolean = FirebaseApp.getApps(context)
        .firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME } != null

    fun initializeFirebase(context: Context) {
        if (firebaseAppExists(context)) {
            FirebaseApp.getInstance().delete()
        }
        FirebaseApp.initializeApp(context, getFirebaseOptions())
    }

    fun getEnvironment(): L15EnvironmentKey = when (getAppEnvironment()) {
        DEV_ENV -> L15EnvironmentKey.DEV
        else -> L15EnvironmentKey.PROD
    }

    external fun getPuzzleUrl(): String

    external fun getPuzzlePreset(): String
}