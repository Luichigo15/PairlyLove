package app.luichigo15.pairly.ui.home.boy.screen.gift

import android.util.Log
import androidx.lifecycle.ViewModel
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.ui.home.boy.screen.gift.model.PLBoyGiftEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PLBoyGiftViewModel @Inject constructor() : ViewModel() {

    private val _giftData = MutableStateFlow(PLGift())
    val giftData = _giftData.asStateFlow()

    private val _uiState = MutableStateFlow<L15Result<Boolean, PLErrorCodes>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    fun onGiftEvent(event: PLBoyGiftEvent){
        when(event){
            is PLBoyGiftEvent.DateChanged -> _giftData.update { it.setExpiresOn(event.date) }
            is PLBoyGiftEvent.NameChanged -> _giftData.update { it.setName(event.name) }
            PLBoyGiftEvent.ResetState -> _uiState.update { L15Result.Start }
            PLBoyGiftEvent.Submit -> createGift()
        }
    }

    private fun createGift(){
    }

}