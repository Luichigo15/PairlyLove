package app.luichigo15.pairly.ui.home.common.home.model

sealed class PLHomeEvent {
    data class RequestNotificationPermission(val granted: Boolean) : PLHomeEvent()
}