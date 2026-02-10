package app.luichigo15.pairly.ui.home.boy.screen.gift.model

sealed class PLBoyGiftEvent {
    data class NameChanged(val name: String) : PLBoyGiftEvent()
    data class DateChanged(val date: Long) : PLBoyGiftEvent()
    data object Submit : PLBoyGiftEvent()
    data object ResetState : PLBoyGiftEvent()
}