package app.luichigo15.pairly

import android.app.Application
import app.luichigo15.common.utils.L15Logger
import app.luichigo15.pairly.environment.PLEnvironment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PLApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        PLEnvironment.initializeFirebase(this)
        L15Logger.setEnvironment(PLEnvironment.getEnvironment())
    }
}