package app.luichigo15.pairly.domain.provider

import kotlinx.coroutines.flow.StateFlow

interface PLUserDataProvider {
    val pairCode: StateFlow<String>
    val role: StateFlow<String>
}