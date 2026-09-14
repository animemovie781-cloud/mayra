package com.ameya.intelligence.impl.common.mappers

import com.ameya.intelligence.data.remote.api.AmeyaProviderRegistry
import com.ameya.intelligence.data.remote.api.ConfiguredModel
import com.ameya.intelligence.data.remote.api.ProviderConnection
import com.ameya.intelligence.domain.models.ModelOption

object ModelUiMapper {
    fun mapConnectionModel(
        connection: ProviderConnection,
        model: ConfiguredModel
    ): ModelOption? {
        if (!model.enabled) return null
        return ModelOption(
            id = "model|${connection.id}|${model.id}",
            name = model.displayName.ifBlank { model.id },
            modelId = model.id,
            connectionId = connection.id,
            providerId = connection.providerId,
            providerName = connection.name.ifBlank {
                AmeyaProviderRegistry.displayName(connection.providerId)
            },
            iconType = connection.providerId,
            supportsImages = model.supportsImages
        )
    }

    fun mapRemoteModel(
        id: String,
        name: String,
        modelId: String,
        providerId: String = "",
        providerName: String = "",
        tagTitle: String? = null,
        quotaLabel: String? = null,
        resetTime: String? = null,
        supportsImages: Boolean = false
    ): ModelOption = ModelOption(
        id = id,
        name = name.ifBlank { modelId },
        modelId = modelId,
        providerId = providerId,
        providerName = providerName,
        tagTitle = tagTitle,
        quotaLabel = quotaLabel,
        resetTime = resetTime,
        isRemote = true,
        iconType = providerId.ifBlank { "default" },
        supportsImages = supportsImages
    )
}
