package app.luichigo15.pairly.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.luichigo15.pairly.common.PLRoleConst
import app.luichigo15.pairly.ui.home.boy.PLBoyHomeActivity
import app.luichigo15.pairly.ui.home.girl.PLGirlHomeActivity
import app.luichigo15.pairly.ui.role.PLRoleActivity
import app.luichigo15.pairly.ui.theme.PLTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class PLSplashActivity : ComponentActivity() {

    private val splashViewModel: PLSplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initState()
        enableEdgeToEdge()
        setContent {
            PLTheme {
                PLSplashScreen(onAnimationFinished = splashViewModel::onAnimationFinished)
            }
        }
    }

    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.splashState.collect { splashState ->
                    if (!splashState.isAnimationFinished) return@collect

                    chooseActivity(splashState.role)
                }
            }
        }
    }

    private fun chooseActivity(role: String) {
        val activity = when (role) {
            PLRoleConst.BOY_ROLE -> PLBoyHomeActivity::class.java
            PLRoleConst.GIRL_ROLE -> PLGirlHomeActivity::class.java
            else -> PLRoleActivity::class.java
        }
        startActivity(Intent(this, activity))
        finish()
    }
}