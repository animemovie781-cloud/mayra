package com.ameya.intelligence.ui.activities.models

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.ui.screens.models.ProviderDetailScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectionId = intent.getStringExtra(EXTRA_CONNECTION_ID)
            ?: throw IllegalArgumentException("connectionId is required")
            
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                ProviderDetailScreen(
                    connectionId = connectionId,
                    onNavigateBack = { finish() }
                )
            }
        }
    }

    companion object {
        private const val EXTRA_CONNECTION_ID = "connection_id"
        
        fun start(activity: Activity, connectionId: String) {
            val intent = Intent(activity, ProviderDetailActivity::class.java).apply {
                putExtra(EXTRA_CONNECTION_ID, connectionId)
            }
            activity.startActivity(intent)
        }
    }
}
