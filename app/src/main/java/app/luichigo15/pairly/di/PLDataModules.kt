package app.luichigo15.pairly.di

import android.content.Context
import app.luichigo15.pairly.data.preferences.PLPreferencesImpl
import app.luichigo15.pairly.di.domain.preferences.PLPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PLDatabaseModule {
}

@Module
@InstallIn(SingletonComponent::class)
object PLUtilsModule {
    @Provides
    fun providesPreferences(context: Context): PLPreferences = PLPreferencesImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface PLFirebaseModule {
}

@Module
@InstallIn(SingletonComponent::class)
interface PLRepositoryModule {
}