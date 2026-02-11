package app.luichigo15.pairly.ui.home.girl.screen.gift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.domain.usecase.gift.PLObserveGiftsUseCase
import app.luichigo15.pairly.domain.usecase.gift.PLSyncGiftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PLGirlGiftViewModel @Inject constructor(
    private val syncGiftsUseCase: PLSyncGiftsUseCase,
    private val observeGiftsUseCase: PLObserveGiftsUseCase
) : ViewModel() {

    val gifts =
        observeGiftsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        syncGiftsUseCase().launchIn(viewModelScope)
    }
}