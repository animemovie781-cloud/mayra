package com.ameya.intelligence.ui.activities.bridge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ameya.intelligence.domain.ai.IntelligenceSessionManager
import com.ameya.intelligence.ui.screens.chat.bridge.WindowsBridgeChatScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WindowsBridgeChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AmeyaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel: com.ameya.intelligence.ui.viewmodels.ChatViewModel = hiltViewModel()

                    LaunchedEffect(Unit) {
                        mainViewModel.switchMode(IntelligenceSessionManager.SessionMode.WINDOWS_BRIDGE)
                    }

                    DisposableEffect(Unit) {
                        onDispose {
                            mainViewModel.switchMode(IntelligenceSessionManager.SessionMode.LOCAL)
                        }
                    }

                    WindowsBridgeChatScreen(
                        viewModel = mainViewModel,
                        onNavigateToSettings = {
                            com.ameya.intelligence.ui.activities.settings.local.LocalSettingsActivity.start(this@WindowsBridgeChatActivity)
                        },
                        onNavigateToWorkspace = {
                            // Windows Bridge chat has no separate workspace screen;
                            // reuse the Remote Session screen for reconfiguring the
                            // connection.
                            com.ameya.intelligence.ui.activities.antigravity.RemoteSessionActivity
                                .start(this@WindowsBridgeChatActivity)
                        },
                        onNavigateToOpencode = {
                            com.ameya.intelligence.ui.activities.opencode.OpencodeSessionActivity
                                .start(this@WindowsBridgeChatActivity)
                        },
                        onExit = { finish() }
                    )
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WindowsBridgeChatActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }
}
