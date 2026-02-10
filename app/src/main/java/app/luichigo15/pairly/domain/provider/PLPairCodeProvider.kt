package app.luichigo15.pairly.domain.provider

import kotlinx.coroutines.flow.StateFlow

interface PLPairCodeProvider {
    val pairCode: StateFlow<String>
}