package app.luichigo15.pairly.ui.home.girl.screen.gift.model

sealed class PLGirlGiftEvent {
    data class GiftRedeemed(val id: String) : PLGirlGiftEvent()
}