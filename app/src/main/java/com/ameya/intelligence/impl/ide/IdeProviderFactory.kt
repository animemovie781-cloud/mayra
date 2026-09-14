package com.ameya.intelligence.impl.ide

import com.ameya.intelligence.domain.ai.IdeProvider
import com.ameya.intelligence.domain.models.IdeInfo

/**
 * Factory for IDE providers using the Plugin pattern.
 * New IDEs register themselves here.
 */
object IdeProviderFactory {
    private val providers = mutableMapOf<String, IdeProvider>()
    
    init {
        // Register default providers
        register(com.ameya.intelligence.impl.ide.antigravity.AntigravityProvider)
        register(com.ameya.intelligence.impl.ide.opencode.OpencodeProvider)
        register(com.ameya.intelligence.impl.ide.cursor.CursorProvider)
        register(com.ameya.intelligence.impl.ide.windsurf.WindsurfProvider)
    }
    
    fun register(provider: IdeProvider) {
        providers[provider.ideId] = provider
    }
    
    fun get(ideId: String): IdeProvider? = providers[ideId]
    
    fun getAll(): List<IdeProvider> = providers.values.toList()
    
    fun getEnabled(): List<IdeProvider> = providers.values.filter { it.isEnabled }
    
    fun getIdeInfo(ideId: String): IdeInfo? = providers[ideId]?.info
}
