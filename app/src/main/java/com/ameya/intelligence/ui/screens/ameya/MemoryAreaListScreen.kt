package com.ameya.intelligence.ui.screens.ameya

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ameya.intelligence.data.repository.MemoryRecord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryAreaListScreen(
    area: MemoryArea,
    state: AmeyaUiState,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onAdd: (String) -> Unit,
    onDelete: (MemoryRecord) -> Unit = {}
) {
    val colors = iosAmeyaColors()
    var showAddSheet by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf<MemoryRecord?>(null) }

    val records = when (area) {
        MemoryArea.USER -> state.userMemoryRecords
        MemoryArea.PROJECT -> state.projectMemoryRecords
    }
    Box(modifier = Modifier.fillMaxSize().background(colors.groupedBackground)) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = AmeyaGroupedSettingsTokens.contentHorizontalPadding,
                end = AmeyaGroupedSettingsTokens.contentHorizontalPadding,
                bottom = AmeyaGroupedSettingsTokens.floatingActionButtonContentClearance
            ),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(AmeyaGroupedSettingsTokens.sectionSpacing)
        ) {
            item {
                Spacer(
                    Modifier
                        .statusBarsPadding()
                        .height(AmeyaGroupedSettingsTokens.screenContentTopSpacer)
                )
            }
            item {
                if (records.isNotEmpty()) {
                    AmeyaSection("Saved") {
                        records.forEachIndexed { index, record ->
                            MemoryRecordCard(record = record, colors = colors, onDelete = { deleting = record })
                            if (index < records.lastIndex) AmeyaDivider()
                        }
                    }
                } else {
                    com.ameya.intelligence.ui.components.shared.SettingsEmptyState(
                        title = "No saved items",
                        subtitle = if (area == MemoryArea.PROJECT && state.workspacePath == null) "Select a workspace first" else "Add a memory to get started",
                        icon = if (area == MemoryArea.PROJECT) androidx.compose.material.icons.Icons.Default.FolderOpen else androidx.compose.material.icons.Icons.Default.Person,
                        modifier = Modifier.padding(top = AmeyaGroupedSettingsTokens.emptyStateListTopSpacing)
                    )
                }
            }
        }

        AmeyaTopScrim(Modifier.align(Alignment.TopCenter))

        TopAppBar(
            title = {
                Text(
                    area.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = AmeyaGroupedSettingsTokens.topBarTitleStartPadding),
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                com.ameya.intelligence.ui.components.shared.SettingsBackButton(onClick = onNavigateBack)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ),
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = AmeyaGroupedSettingsTokens.topBarHorizontalPadding),
            windowInsets = WindowInsets(0.dp)
        )

        val showFab = area != MemoryArea.PROJECT || state.workspacePath != null
        if (showFab) {
            androidx.compose.material3.ExtendedFloatingActionButton(
                onClick = { showAddSheet = true },
                icon = { androidx.compose.material3.Icon(Icons.Default.Add, "Add Memory") },
                text = { androidx.compose.material3.Text("Memory") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(4.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = AmeyaGroupedSettingsTokens.floatingActionButtonInset)
                    .ameyaFloatingActionButtonBottomPadding()
            )
        }
    }

    if (showAddSheet) {
        AddMemorySheet(
            area = area,
            onDismiss = { showAddSheet = false },
            onAdd = onAdd
        )
    }
    deleting?.let { record -> androidx.compose.material3.AlertDialog(
        onDismissRequest = { deleting = null },
        title = { Text("Delete memory?") },
        text = { Text(record.content) },
        confirmButton = { androidx.compose.material3.TextButton(onClick = { onDelete(record); deleting = null }) { Text("Delete") } },
        dismissButton = { androidx.compose.material3.TextButton(onClick = { deleting = null }) { Text("Cancel") } }
    ) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddMemorySheet(
    area: MemoryArea,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    val colors = iosAmeyaColors()
    var text by remember { mutableStateOf("") }

    com.ameya.intelligence.ui.components.shared.StandardModalBottomSheet(
        onDismissRequest = onDismiss,
        title = "Add ${area.title}"
    ) {
        val cancel = { dismiss() }
        val save = { dismiss { onAdd(text) } }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(area.inputLabel()) },
            minLines = 2,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.primaryText)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = cancel) {
                Text("Cancel")
            }
            Spacer(Modifier.width(AmeyaGroupedSettingsTokens.rowIconTextGap))
            Button(
                onClick = save,
                enabled = text.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun MemoryRecordCard(record: MemoryRecord, colors: com.ameya.intelligence.ui.screens.ameya.IosAmeyaColors, onDelete: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AmeyaGroupedSettingsTokens.rowHorizontalPadding,
                vertical = AmeyaGroupedSettingsTokens.rowVerticalPadding
            ),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(AmeyaGroupedSettingsTokens.rowTextSpacing)
        ) {
            Text(
                record.content,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = colors.primaryText
            )
            if (record.reason.isNotBlank()) {
                Text(
                    record.reason,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.secondaryText
                )
            }
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, "Delete memory", tint = MaterialTheme.colorScheme.error)
        }
    }
}

private fun MemoryArea.inputLabel(): String = when (this) {
    MemoryArea.USER -> "Preference or profile fact"
    MemoryArea.PROJECT -> "Workspace or project fact"
}

