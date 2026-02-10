package app.luichigo15.pairly.di

import android.content.Context
import app.luichigo15.pairly.data.firebase.PLFirestoreImpl
import app.luichigo15.pairly.data.firebase.PLPushNotificationsImpl
import app.luichigo15.pairly.data.preferences.PLPreferencesImpl
import app.luichigo15.pairly.data.provider.PLPairCodeProviderImpl
import app.luichigo15.pairly.data.repository.PLGiftRepositoryImpl
import app.luichigo15.pairly.data.repository.PLUserRepositoryImpl
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.firebase.PLPushNotifications
import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLPairCodeProvider
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import app.luichigo15.pairly.domain.repository.PLUserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PLDatabaseModule {
}

@Module
@InstallIn(SingletonComponent::class)
object PLUtilsModule {
    @Provides
    fun providesPreferences(@ApplicationContext context: Context): PLPreferences = PLPreferencesImpl(context)
}

@Module
@InstallIn(SingletonComponent::class)
interface PLFirebaseModule {
    @Binds
    fun providesFirestore(firestoreImpl: PLFirestoreImpl): PLFirestore

    @Binds
    fun providesPushNotifications(pushNotifications: PLPushNotificationsImpl): PLPushNotifications
}

@Module
@InstallIn(SingletonComponent::class)
interface PLRepositoryModule {
    @Binds
    fun providesUserRepository(userRepository: PLUserRepositoryImpl): PLUserRepository

    @Binds
    fun providesGiftRepository(giftRepository: PLGiftRepositoryImpl): PLGiftRepository
}

@Module
@InstallIn(SingletonComponent::class)
interface PLPairCodeModule {

    @Binds
    @Singleton
    fun providesPairCodeProvider(pairCodeProvider: PLPairCodeProviderImpl): PLPairCodeProvider
}