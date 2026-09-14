package com.ameya.intelligence.ui.activities.mcp.local

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.data.remote.api.AiSettingsManager
import com.ameya.intelligence.ui.screens.mcp.local.LocalMcpScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalMcpActivity : AppCompatActivity() {

    @Inject
    lateinit var aiSettingsManager: AiSettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                LocalMcpScreen(
                    onNavigateBack = { finish() },
                    aiSettingsManager = aiSettingsManager
                )
            }
        }
    }

    companion object {
        fun start(activity: android.app.Activity) {
            activity.startActivity(android.content.Intent(activity, LocalMcpActivity::class.java))
        }
    }
}
