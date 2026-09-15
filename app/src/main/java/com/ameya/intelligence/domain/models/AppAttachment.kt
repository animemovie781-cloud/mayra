package com.ameya.intelligence.domain.models

import android.net.Uri

enum class AttachmentType {
    IMAGE, DOCUMENT, CODE, ARCHIVE, VIDEO, AUDIO, OTHER
}

data class AppAttachment(
    val uri: Uri,
    val name: String,
    val mimeType: String?,
    val size: Long,
    val type: AttachmentType,
    val base64: String? = null // Filled right before sending if provider requires base64
)
