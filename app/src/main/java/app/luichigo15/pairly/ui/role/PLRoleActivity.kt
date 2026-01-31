package app.luichigo15.pairly.ui.role

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.luichigo15.pairly.ui.theme.PLTheme

class PLRoleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLTheme {
                PLRoleScreen()
            }
        }
    }
}