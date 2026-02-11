package app.luichigo15.pairly.ui.home.boy.screen.gift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.luichigo15.common.utils.L15Result
import app.luichigo15.pairly.common.PLErrorCodes
import app.luichigo15.pairly.domain.model.PLGift
import app.luichigo15.pairly.domain.usecase.gift.PLCreateGiftUseCase
import app.luichigo15.pairly.ui.home.boy.screen.gift.model.PLBoyGiftEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PLBoyGiftViewModel @Inject constructor(
    private val createGiftUseCase: PLCreateGiftUseCase
) : ViewModel() {

    private val _giftData = MutableStateFlow(PLGift())
    val giftData = _giftData.asStateFlow()

    private val _uiState = MutableStateFlow<L15Result<Boolean, PLErrorCodes>>(L15Result.Start)
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: PLBoyGiftEvent){
        when(event){
            is PLBoyGiftEvent.DateChanged -> _giftData.update { it.setExpiresOn(event.date) }
            is PLBoyGiftEvent.NameChanged -> _giftData.update { it.setName(event.name) }
            PLBoyGiftEvent.ResetState -> _uiState.update { L15Result.Start }
            PLBoyGiftEvent.Submit -> createGift()
        }
    }

    private fun createGift(){
        viewModelScope.launch {
            createGiftUseCase(_giftData.value).collect { result ->
                when (result) {
                    is L15Result.Error -> _uiState.update { L15Result.Error(result.error) }
                    L15Result.Loading -> _uiState.update { L15Result.Loading }
                    L15Result.Start -> {}
                    is L15Result.Success -> _uiState.update { L15Result.Success(true) }
                }
            }
        }
    }

}