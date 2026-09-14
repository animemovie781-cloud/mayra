package com.ameya.intelligence.ui.activities.cronjob.remote

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.ui.screens.cronjob.remote.RemoteCronJobScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme

class RemoteCronJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                RemoteCronJobScreen(
                    onNavigateBack = { finish() }
                )
            }
        }
    }

    companion object {
        fun start(activity: android.app.Activity) {
            activity.startActivity(android.content.Intent(activity, RemoteCronJobActivity::class.java))
        }
    }
}
