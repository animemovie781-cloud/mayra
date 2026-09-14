package com.ameya.intelligence.ui.screens.cronjob.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ameya.intelligence.data.local.entity.CronJobEntity
import com.ameya.intelligence.ui.components.shared.SettingsEmptyState
import com.ameya.intelligence.ui.screens.ameya.AmeyaGroupedSettingsTokens
import com.ameya.intelligence.ui.screens.settings.shared.SettingsSectionCard

import com.ameya.intelligence.ui.screens.ameya.iosAmeyaColors

@Composable
fun CronJobList(
    jobs: List<CronJobEntity>,
    onToggle: (CronJobEntity, Boolean) -> Unit,
    onDelete: (CronJobEntity) -> Unit,
    topPadding: androidx.compose.ui.unit.Dp = 72.dp,
    modifier: Modifier = Modifier
) {
    val colors = iosAmeyaColors()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AmeyaGroupedSettingsTokens.sectionSpacing),
        contentPadding = PaddingValues(
            start = AmeyaGroupedSettingsTokens.contentHorizontalPadding,
            end = AmeyaGroupedSettingsTokens.contentHorizontalPadding,
            top = topPadding,
            bottom = AmeyaGroupedSettingsTokens.screenContentBottomSpacer
        )
    ) {
        if (jobs.isEmpty()) {
            item {
                SettingsEmptyState(
                    title = "No reminders yet",
                    subtitle = "Tap + to schedule a reminder",
                    icon = Icons.Default.Alarm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AmeyaGroupedSettingsTokens.emptyStateListTopSpacing)
                )
            }
        }

        if (jobs.isNotEmpty()) {
            item {
                SettingsSectionCard(title = "Automation") {
                    jobs.forEachIndexed { index, job ->
                        CronJobCard(
                            job = job,
                            onToggle = { active -> onToggle(job, active) },
                            onDelete = { onDelete(job) }
                        )
                        if (index < jobs.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 58.dp, end = 16.dp),
                                color = colors.separator,
                                thickness = 0.7.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
