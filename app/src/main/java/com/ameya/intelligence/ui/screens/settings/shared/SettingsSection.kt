package com.ameya.intelligence.ui.screens.settings.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import com.ameya.intelligence.ui.screens.ameya.AmeyaGroupedSettingsTokens
import com.ameya.intelligence.ui.screens.ameya.iosAmeyaColors

@Composable
fun SettingsSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = iosAmeyaColors()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AmeyaGroupedSettingsTokens.sectionHeaderSpacing)
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = colors.headerText,
            modifier = Modifier.padding(start = AmeyaGroupedSettingsTokens.sectionHeaderStartPadding)
        )
        Surface(
            shape = RoundedCornerShape(AmeyaGroupedSettingsTokens.sectionCornerRadius),
            color = colors.groupSurface,
            border = BorderStroke(AmeyaGroupedSettingsTokens.sectionBorderWidth, colors.border),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(content = content)
        }
    }
}
