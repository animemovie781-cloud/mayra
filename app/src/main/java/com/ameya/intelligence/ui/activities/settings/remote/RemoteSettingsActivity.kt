package com.ameya.intelligence.ui.activities.settings.remote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.ui.activities.opencode.OpencodeSettingsActivity
import com.ameya.intelligence.ui.screens.settings.remote.RemoteSettingsScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme

class RemoteSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                RemoteSettingsScreen(
                    onNavigateBack = { finish() },
                    onNavigateToOpencode = {
                        OpencodeSettingsActivity.start(this@RemoteSettingsActivity)
                    }
                )
            }
        }
    }

    companion object {
        fun start(activity: android.app.Activity) {
            activity.startActivity(android.content.Intent(activity, RemoteSettingsActivity::class.java))
        }
    }
}
