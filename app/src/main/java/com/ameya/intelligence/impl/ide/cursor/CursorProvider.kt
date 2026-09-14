package com.ameya.intelligence.impl.ide.cursor

import com.ameya.intelligence.domain.ai.IdeProvider
import com.ameya.intelligence.domain.models.IdeCapabilities
import com.ameya.intelligence.domain.models.IdeInfo

/**
 * Cursor IDE provider placeholder.
 */
object CursorProvider : IdeProvider {
    
    override val ideId: String = "cursor"
    
    override val info: IdeInfo = IdeInfo(
        id = ideId,
        displayName = "Cursor",
        description = "Cursor AI Code Editor",
        capabilities = IdeCapabilities(
            requiresConnection = true
        )
    )
    
    override val isEnabled: Boolean = false // Placeholder but clickable
}
