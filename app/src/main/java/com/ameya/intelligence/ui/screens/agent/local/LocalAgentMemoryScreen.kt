package com.ameya.intelligence.ui.screens.agent.local

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ameya.intelligence.data.repository.AgentMemoryRecord
import com.ameya.intelligence.ui.screens.ameya.AmeyaDivider
import com.ameya.intelligence.ui.screens.ameya.AmeyaNavigationRow
import com.ameya.intelligence.ui.screens.ameya.AmeyaScaffold
import com.ameya.intelligence.ui.screens.ameya.AmeyaSection

@Composable
fun LocalAgentMemoryScreen(
    records: List<AgentMemoryRecord>,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onAdd: (String) -> Unit,
    onDelete: (AgentMemoryRecord) -> Unit
) {
    var adding by remember { mutableStateOf(false) }
    var deleting by remember { mutableStateOf<AgentMemoryRecord?>(null) }
    var content by remember { mutableStateOf("") }
    AmeyaScaffold("Agent Memory", snackbarHostState, onNavigateBack) {
        AmeyaSection("Private to this agent") {
            AmeyaNavigationRow(Icons.Default.Add, "Add Memory", "Saved only for this agent", onClick = { adding = true })
            records.forEach { record ->
                AmeyaDivider()
                androidx.compose.foundation.layout.Row(Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(record.title)
                        Text(record.content)
                    }
                    IconButton(onClick = { deleting = record }) {
                        Icon(Icons.Default.Delete, "Delete memory", tint = androidx.compose.material3.MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
    if (adding) AlertDialog(
        onDismissRequest = { adding = false },
        title = { Text("Add Agent Memory") },
        text = { OutlinedTextField(content, { content = it }, Modifier.fillMaxWidth(), label = { Text("Memory") }, minLines = 3) },
        confirmButton = { TextButton(enabled = content.isNotBlank(), onClick = { onAdd(content.trim()); content = ""; adding = false }) { Text("Save") } },
        dismissButton = { TextButton(onClick = { adding = false }) { Text("Cancel") } }
    )
    deleting?.let { record -> AlertDialog(
        onDismissRequest = { deleting = null },
        title = { Text("Delete memory?") },
        text = { Text(record.content) },
        confirmButton = { TextButton(onClick = { onDelete(record); deleting = null }) { Text("Delete") } },
        dismissButton = { TextButton(onClick = { deleting = null }) { Text("Cancel") } }
    ) }
}
