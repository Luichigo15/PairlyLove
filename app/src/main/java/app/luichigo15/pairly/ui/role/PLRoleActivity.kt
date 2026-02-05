package app.luichigo15.pairly.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.ui.home.boy.PLBoyHomeActivity
import app.luichigo15.pairly.ui.home.girl.PLGirlHomeActivity
import app.luichigo15.pairly.ui.theme.PLTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PLRoleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PLTheme {
                PLRoleScreen(onNavigateToHome = { role ->
                    val activity =
                        if (role == PLRoleConst.BOY_ROLE) PLBoyHomeActivity::class.java else PLGirlHomeActivity::class.java
                    val intent = Intent(this, activity)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }
}