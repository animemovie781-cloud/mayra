package com.ameya.intelligence.ui.activities.settings.local

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.data.repository.TerminalSettingsRepository
import com.ameya.intelligence.ui.screens.settings.local.TerminalSettingsScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocalTerminalSettingsActivity : AppCompatActivity() {
    @Inject lateinit var repository: TerminalSettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                TerminalSettingsScreen(repository = repository, onNavigateBack = { finish() })
            }
        }
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, LocalTerminalSettingsActivity::class.java))
        }
    }
}
