package com.ameya.intelligence.ui.activities.agent.local

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.lifecycleScope
import com.ameya.intelligence.data.local.dao.AgentDao
import com.ameya.intelligence.data.local.dao.ConversationDao
import com.ameya.intelligence.data.local.entity.AgentEntity
import com.ameya.intelligence.data.repository.ReferenceDocumentRepository
import com.ameya.intelligence.domain.models.AssistantMode
import com.ameya.intelligence.ui.activities.project.local.LocalProjectActivity
import com.ameya.intelligence.ui.activities.shared.LocalReferenceListActivity
import com.ameya.intelligence.ui.screens.agent.local.LocalAgentDetailScreen
import com.ameya.intelligence.ui.theme.AmeyaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocalAgentDetailActivity : AppCompatActivity() {
    @Inject lateinit var agentDao: AgentDao
    @Inject lateinit var conversationDao: ConversationDao
    @Inject lateinit var referenceDocumentRepository: ReferenceDocumentRepository
    @Inject lateinit var agentMemoryRepository: com.ameya.intelligence.data.repository.AgentMemoryRepository
    private val groupId by lazy { intent.getLongExtra(EXTRA_GROUP_ID, -1L) }
    private var group by mutableStateOf<com.ameya.intelligence.data.local.entity.AgentGroupEntity?>(null)
    private var loading by mutableStateOf(true)

    private val workspacePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        val workspace = result.data?.getStringExtra(LocalProjectActivity.RESULT_KEY) ?: return@registerForActivityResult
        lifecycleScope.launch {
            agentDao.getGroupById(groupId)?.let {
                val updated = it.copy(workspacePath = workspace, updatedAt = System.currentTimeMillis())
                group = updated
                agentDao.updateGroup(updated)
            }
        }
    }
    private val referencePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri ?: return@registerForActivityResult
        lifecycleScope.launch {
            val group = agentDao.getGroupById(groupId) ?: return@launch
            referenceDocumentRepository.import("agent_group", group.id, uri).onSuccess { path ->
                agentDao.updateGroup(group.copy(referencePathsJson = referenceDocumentRepository.appendPath(group.referencePathsJson, path), updatedAt = System.currentTimeMillis()))
            }.onFailure { android.widget.Toast.makeText(this@LocalAgentDetailActivity, it.message, android.widget.Toast.LENGTH_LONG).show() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmeyaTheme {
                val allAgents by agentDao.observeAll().collectAsState(initial = emptyList())
                val agents = allAgents.filter { it.groupId == groupId }
                androidx.compose.runtime.LaunchedEffect(groupId) {
                    group = agentDao.getGroupById(groupId)
                    loading = false
                }
                val currentGroup = group
                if (loading) {
                    androidx.compose.foundation.layout.Box(androidx.compose.ui.Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        androidx.compose.material3.CircularProgressIndicator()
                    }
                } else if (currentGroup == null) {
                    androidx.compose.runtime.LaunchedEffect(Unit) { finish() }
                } else LocalAgentDetailScreen(
                    group = currentGroup,
                    agents = agents,
                    snackbarHostState = remember { SnackbarHostState() },
                    onNavigateBack = { finish() },
                    onSaveGroupName = { name ->
                        val updated = currentGroup.copy(name = name, updatedAt = System.currentTimeMillis())
                        group = updated
                        lifecycleScope.launch { agentDao.updateGroup(updated) }
                    },
                    onSaveGroupInstructions = { instructions ->
                        val updated = currentGroup.copy(instructions = instructions, updatedAt = System.currentTimeMillis())
                        group = updated
                        lifecycleScope.launch { agentDao.updateGroup(updated) }
                    },
                    onSelectWorkspace = { workspacePicker.launch(LocalProjectActivity.workspacePickerIntent(this)) },
                    onAddReference = { LocalReferenceListActivity.start(this, "agent_group", currentGroup.id) },
                    onOpenAgent = { LocalAgentConfigActivity.startForResult(this, it.id) },
                    onCreateAgent = { name, role, instructions -> lifecycleScope.launch { agentDao.insert(AgentEntity(groupId = currentGroup.id, name = name, role = role, instructions = instructions)) } },
                    onDeleteGroup = {
                        lifecycleScope.launch {
                            conversationDao.deleteOwnedConversations(AssistantMode.AGENT.name, currentGroup.id.toString())
                            referenceDocumentRepository.deleteOwner("agent_group", currentGroup.id)
                            agents.forEach { agent ->
                                referenceDocumentRepository.deleteOwner("agent", agent.id)
                                agentMemoryRepository.deleteOwner(agent.id)
                            }
                            agentDao.deleteGroup(currentGroup)
                            setResult(RESULT_DELETED)
                            finish()
                        }
                    }
                )
            }
        }
    }

    companion object {
        private const val EXTRA_GROUP_ID = "group_id"
        const val REQUEST_CODE = 1013
        const val RESULT_DELETED = Activity.RESULT_FIRST_USER + 2
        fun startForResult(activity: Activity, groupId: Long) {
            activity.startActivityForResult(Intent(activity, LocalAgentDetailActivity::class.java).putExtra(EXTRA_GROUP_ID, groupId), REQUEST_CODE)
        }
    }
}
