package app.luichigo15.pairly.ui.home.boy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.luichigo15.pairly.ui.theme.PLTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PLBoyHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLTheme {
                PLBoyHomeScreen()
            }
        }
    }
}