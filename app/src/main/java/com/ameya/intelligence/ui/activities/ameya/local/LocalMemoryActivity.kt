package com.ameya.intelligence.ui.activities.ameya.local

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ameya.intelligence.ui.screens.ameya.AmeyaViewModel
import com.ameya.intelligence.ui.screens.ameya.MemoryScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalMemoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                val viewModel: AmeyaViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsState()
                LaunchedEffect(Unit) { viewModel.setWorkspace(intent.getStringExtra(EXTRA_WORKSPACE)) }
                val snackbarHostState = remember { SnackbarHostState() }
                LaunchedEffect(state.message) { state.message?.let { snackbarHostState.showSnackbar(it); viewModel.clearMessage() } }
                MemoryScreen(
                    state = state,
                    snackbarHostState = snackbarHostState,
                    onNavigateBack = { finish() },
                    onToggleUseSavedMemory = { value -> viewModel.updateMemorySettings { it.copy(useSavedMemory = value) } },
                    onOpenAboutYou = { LocalMemoryAreaActivity.start(this, com.ameya.intelligence.ui.screens.ameya.MemoryArea.USER) }
                )
            }
        }
    }

    companion object {
        private const val EXTRA_WORKSPACE = "workspace_path"
        fun start(activity: Activity, workspacePath: String? = null) {
            activity.startActivity(Intent(activity, LocalMemoryActivity::class.java).putExtra(EXTRA_WORKSPACE, workspacePath))
        }
    }
}
