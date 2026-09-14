package com.ameya.intelligence.ui.screens.ameya

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun AmeyaHomeScreen(
    state: AmeyaUiState,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onMemory: () -> Unit,
    onReview: () -> Unit,
    onSkills: () -> Unit
) {
    AmeyaScaffold("Ameya", snackbarHostState, onNavigateBack) {
        if (state.pendingProposals.isNotEmpty()) {
            AmeyaSection("Needs Attention") {
                AmeyaNavigationRow(
                    icon = Icons.Default.SettingsSuggest,
                    title = "${state.pendingProposals.size} suggestion${if (state.pendingProposals.size == 1) "" else "s"} need review",
                    subtitle = "Save or dismiss memory and context updates",
                    onClick = onReview
                )
            }
        }
        AmeyaSection("Settings") {
            AmeyaNavigationRow(Icons.Default.Memory, "Memory", "${state.totalMemoryCount} saved items", onMemory)
            AmeyaDivider()
            AmeyaNavigationRow(Icons.Default.Psychology, "Skills", "${state.enabledSkills} enabled workflows", onSkills)
        }
    }
}
