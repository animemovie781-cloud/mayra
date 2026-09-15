package com.ameya.intelligence.domain.models

object AttachmentValidator {
    const val MAX_IMAGE_SIZE = 20 * 1024 * 1024L // 20 MB
    const val MAX_DOC_SIZE = 50 * 1024 * 1024L // 50 MB
    const val MAX_CODE_SIZE = 20 * 1024 * 1024L // 20 MB
    const val MAX_ARCHIVE_SIZE = 100 * 1024 * 1024L // 100 MB
    const val MAX_TOTAL_SIZE = 100 * 1024 * 1024L // 100 MB
    const val MAX_ATTACHMENTS = 5

    fun validateAttachment(attachment: AppAttachment): Result<Unit> {
        val maxSize = when (attachment.type) {
            AttachmentType.IMAGE -> MAX_IMAGE_SIZE
            AttachmentType.DOCUMENT -> MAX_DOC_SIZE
            AttachmentType.CODE -> MAX_CODE_SIZE
            AttachmentType.ARCHIVE -> MAX_ARCHIVE_SIZE
            else -> MAX_DOC_SIZE // fallback
        }

        return if (attachment.size > maxSize) {
            Result.failure(Exception("File ${attachment.name} exceeds the limit of ${maxSize / (1024 * 1024)} MB"))
        } else {
            Result.success(Unit)
        }
    }

    fun validateAttachments(attachments: List<AppAttachment>): Result<Unit> {
        if (attachments.size > MAX_ATTACHMENTS) {
            return Result.failure(Exception("Maximum $MAX_ATTACHMENTS attachments allowed"))
        }

        var totalSize = 0L
        for (att in attachments) {
            val result = validateAttachment(att)
            if (result.isFailure) return result
            totalSize += att.size
        }

        if (totalSize > MAX_TOTAL_SIZE) {
            return Result.failure(Exception("Total attachment size exceeds the limit of ${MAX_TOTAL_SIZE / (1024 * 1024)} MB"))
        }

        return Result.success(Unit)
    }
}
