package app.luichigo15.pairly.domain.firebase

interface PLPushNotifications {
    suspend fun getToken(): String?
}