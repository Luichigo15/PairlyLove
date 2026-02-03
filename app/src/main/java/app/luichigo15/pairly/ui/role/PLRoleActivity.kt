package app.luichigo15.pairly.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.luichigo15.pairly.ui.home.PLHomeActivity
import app.luichigo15.pairly.ui.theme.PLTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PLRoleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLTheme {
                PLRoleScreen(onNavigateToHome = {
                    val intent = Intent(this, PLHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }
}