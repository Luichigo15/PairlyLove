package app.luichigo15.pairly.data.provider

import app.luichigo15.pairly.domain.preferences.PLPreferences
import app.luichigo15.pairly.domain.provider.PLPairCodeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PLPairCodeProviderImpl @Inject constructor(
    preferences: PLPreferences,
) : PLPairCodeProvider {
    override val pairCode: StateFlow<String> =
        preferences.observePairCode
            .stateIn(
                scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
                started = SharingStarted.Eagerly,
                initialValue = ""
            )
}