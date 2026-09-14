package com.ameya.intelligence.ui.activities.mcp.remote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.ui.screens.mcp.remote.RemoteMcpScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme

class RemoteMcpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                RemoteMcpScreen(
                    onNavigateBack = { finish() }
                )
            }
        }
    }

    companion object {
        fun start(activity: android.app.Activity) {
            activity.startActivity(android.content.Intent(activity, RemoteMcpActivity::class.java))
        }
    }
}
