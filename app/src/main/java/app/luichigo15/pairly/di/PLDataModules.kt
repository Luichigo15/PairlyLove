package app.luichigo15.pairly.di

import android.content.Context
import app.luichigo15.common.database.L15DatabaseProvider
import app.luichigo15.common.environment.L15EnvironmentKey
import app.luichigo15.common.network.L15ApiClientBuilder
import app.luichigo15.pairly.data.api.service.PLPuzzleApi
import app.luichigo15.pairly.data.database.PLDatabase
import app.luichigo15.pairly.data.database.dao.PLGiftDao
import app.luichigo15.pairly.data.firebase.PLFirestoreImpl
import app.luichigo15.pairly.data.firebase.PLPushNotificationsImpl
import app.luichigo15.pairly.data.preferences.PLPreferencesImpl
import app.luichigo15.pairly.data.provider.PLUserDataProviderImpl
import app.luichigo15.pairly.data.repository.PLGiftRepositoryImpl
import app.luichigo15.pairly.data.repository.PLPuzzleRepositoryImpl
import app.luichigo15.pairly.data.repository.PLUserRepositoryImpl
import app.luichigo15.pairly.domain.firebase.PLFirestore
import app.luichigo15.pairly.domain.firebase.PLPushNotifications
import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLUserDataProvider
import app.luichigo15.pairly.domain.repository.PLGiftRepository
import app.luichigo15.pairly.domain.repository.PLPuzzleRepository
import app.luichigo15.pairly.domain.repository.PLUserRepository
import app.luichigo15.pairly.environment.PLEnvironment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PLDatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): PLDatabase =
        L15DatabaseProvider.getBuilder(
            context,
            PLDatabase::class.java,
            PLEnvironment.createDatabaseConfig()
        ).build()

    @Provides
    fun providesGiftDao(database: PLDatabase): PLGiftDao = database.giftDao()
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
@InstallIn(ViewModelComponent::class)
interface PLRepositoryModule {
    @Binds
    fun providesUserRepository(userRepository: PLUserRepositoryImpl): PLUserRepository

    @Binds
    fun providesGiftRepository(giftRepository: PLGiftRepositoryImpl): PLGiftRepository

    @Binds
    fun providesPuzzleRepository(puzzleRepository: PLPuzzleRepositoryImpl): PLPuzzleRepository
}

@Module
@InstallIn(SingletonComponent::class)
interface PLUserDataModule {

    @Binds
    @Singleton
    fun providesUserDataProvider(userDataProvider: PLUserDataProviderImpl): PLUserDataProvider
}

@Module
@InstallIn(SingletonComponent::class)
object PLApiModule {

    @Provides
    fun providesPuzzleApi(): PLPuzzleApi = L15ApiClientBuilder(PLPuzzleApi::class.java)
        .setBaseUrl(PLEnvironment.getPuzzleUrl())
        .setActivateLogging(PLEnvironment.getEnvironment() == L15EnvironmentKey.DEV)
        .build()
}