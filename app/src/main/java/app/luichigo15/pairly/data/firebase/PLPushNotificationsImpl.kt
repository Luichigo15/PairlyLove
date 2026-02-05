package app.luichigo15.pairly.data.firebase

import app.luichigo15.common.utils.L15Logger
import app.luichigo15.pairly.domain.firebase.PLPushNotifications
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PLPushNotificationsImpl @Inject constructor() : PLPushNotifications {
    private val TAG = PLPushNotificationsImpl::class.java.simpleName

    private val firebaseMessaging by lazy { FirebaseMessaging.getInstance() }

    override suspend fun getToken(): String? {
        return try {
            val token = firebaseMessaging.token.await()
            L15Logger.d(TAG, "getToken", "Push token: $token")
            token
        } catch (e: Exception) {
            L15Logger.e(TAG, "getToken", "Error getting token $e", e)
            null
        }
    }
}