package app.luichigo15.pairly.ui.home.girl.screen.gift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.pairly.domain.usecase.gift.PLObserveGiftsUseCase
import app.luichigo15.pairly.domain.usecase.gift.PLRedeemGiftUseCase
import app.luichigo15.pairly.domain.usecase.gift.PLSyncGiftsUseCase
import app.luichigo15.pairly.ui.home.girl.screen.gift.model.PLGirlGiftEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLGirlGiftViewModel @Inject constructor(
    syncGiftsUseCase: PLSyncGiftsUseCase,
    observeGiftsUseCase: PLObserveGiftsUseCase,
    private val redeemGiftUseCase: PLRedeemGiftUseCase
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

    fun onEvent(event: PLGirlGiftEvent) {
        when (event) {
            is PLGirlGiftEvent.GiftRedeemed -> viewModelScope.launch { redeemGiftUseCase(event.id) }
        }
    }
}