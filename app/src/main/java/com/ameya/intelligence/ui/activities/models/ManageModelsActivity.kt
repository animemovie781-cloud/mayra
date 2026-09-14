package com.ameya.intelligence.ui.activities.models

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ameya.intelligence.data.remote.api.CodexAuthManager
import com.ameya.intelligence.ui.screens.models.ManageModelsScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManageModelsActivity : AppCompatActivity() {
    @Inject lateinit var codexAuthManager: CodexAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                ManageModelsScreen(
                    codexAuthManager = codexAuthManager,
                    onNavigateBack = { finish() },
                    onNavigateToProvider = { id -> ProviderDetailActivity.start(this, id) }
                )
            }
        }
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ManageModelsActivity::class.java))
        }
    }
}
