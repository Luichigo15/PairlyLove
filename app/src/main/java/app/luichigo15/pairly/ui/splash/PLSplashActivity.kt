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

                    chooseActivity(splashState.pairCode)
                }
            }
        }
    }

    private fun chooseActivity(pairCode: String) {
        val intent = if (pairCode.isEmpty()) Intent(this, PLRoleActivity::class.java)
        else Intent(this, PLRoleActivity::class.java)
        startActivity(intent)
        finish()
    }
}