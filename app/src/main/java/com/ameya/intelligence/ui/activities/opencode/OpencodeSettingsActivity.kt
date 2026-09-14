package com.ameya.intelligence.ui.activities.opencode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ameya.intelligence.ui.screens.opencode.OpencodeSettingsScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpencodeSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpencodeSettingsScreen(
                        onNavigateBack = { finish() }
                    )
                }
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, OpencodeSettingsActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }
}
