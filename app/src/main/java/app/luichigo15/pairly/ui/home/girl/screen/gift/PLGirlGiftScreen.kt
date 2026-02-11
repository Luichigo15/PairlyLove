package app.luichigo15.pairly.ui.home.girl.screen.gift

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.luichigo15.pairly.R
import app.luichigo15.pairly.common.PLCommonConst
import app.luichigo15.pairly.ui.common.PLEmptyScreen
import app.luichigo15.pairly.ui.home.common.PLTopBar
import app.luichigo15.pairly.ui.home.girl.screen.gift.model.PLGirlGiftEvent
import app.luichigo15.pairly.ui.home.girl.screen.gift.widget.PLCouponCard
import app.luichigo15.pairly.ui.home.girl.screen.gift.widget.PLCouponFilter
import app.luichigo15.pairly.ui.theme.PLTheme
import app.luichigo15.pairly.utils.extensions.saveAsShareableFile
import kotlinx.coroutines.launch

@Composable
fun PLGirlGiftScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    giftViewModel: PLGirlGiftViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val gifts by giftViewModel.gifts.collectAsStateWithLifecycle()
    var selectedFilter by remember { mutableIntStateOf(PLCommonConst.VALID_GIFTS_FILTER) }
    val filteredGifts by remember(gifts,selectedFilter) {
        derivedStateOf {
            when(selectedFilter){
                PLCommonConst.VALID_GIFTS_FILTER -> gifts.filter { !it.checkExpired() }
                PLCommonConst.EXPIRED_GIFTS_FILTER -> gifts.filter { it.checkExpired() }
                else -> gifts
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PLTopBar(title = R.string.pl_gifts, onBackClick = onBack)
        if (gifts.isEmpty()) {
            PLEmptyScreen(message = R.string.pl_no_gifts, title = R.string.pl_gifts)
        } else {
            PLCouponFilter(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it })
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(items = filteredGifts, key = { it.id }) { gift ->
                    PLCouponCard(gift = gift, onShare = { graphicsLayer ->
                        scope.launch {
                            if (graphicsLayer.size.width > 0 && graphicsLayer.size.height > 0) {
                                val uri = graphicsLayer.saveAsShareableFile(context)
                                shareCoupon(context, uri)
                                giftViewModel.onEvent(PLGirlGiftEvent.GiftRedeemed(gift.id))
                            }
                        }
                    })
                }
            }
        }
    }
}

private fun shareCoupon(context: Context, couponUri: Uri?) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, couponUri)
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.pl_exchange_coupon_msg))
        type = "image/png"
    }
    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.pl_gifts)))
}

@Preview(showBackground = true)
@Composable
private fun PLGirlGiftScreenPreview() {
    PLTheme(darkTheme = true) {
        PLGirlGiftScreen({})
    }
}