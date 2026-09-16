package com.ameya.intelligence.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageCompressionUtils {
    fun getCompressedBase64(context: Context, uri: Uri): String? {
        return try {
            val maxDim = 2048
            
            // 1. Decode bounds
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }
            
            // 2. Calculate inSampleSize
            var inSampleSize = 1
            if (options.outHeight > maxDim || options.outWidth > maxDim) {
                val halfHeight: Int = options.outHeight / 2
                val halfWidth: Int = options.outWidth / 2
                while (halfHeight / inSampleSize >= maxDim || halfWidth / inSampleSize >= maxDim) {
                    inSampleSize *= 2
                }
            }
            
            // 3. Decode with inSampleSize
            options.inJustDecodeBounds = false
            options.inSampleSize = inSampleSize
            
            var bitmap: Bitmap? = context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            } ?: return null
            
            // 4. Fine-scale if still slightly larger than maxDim
            if (bitmap!!.width > maxDim || bitmap!!.height > maxDim) {
                val aspectRatio = bitmap!!.width.toFloat() / bitmap!!.height.toFloat()
                val targetWidth = if (bitmap!!.width > bitmap!!.height) maxDim else (maxDim * aspectRatio).toInt()
                val targetHeight = if (bitmap!!.height > bitmap!!.width) maxDim else (maxDim / aspectRatio).toInt()
                val scaled = Bitmap.createScaledBitmap(bitmap!!, targetWidth, targetHeight, true)
                if (scaled != bitmap) {
                    bitmap!!.recycle()
                    bitmap = scaled
                }
            }
            
            // 5. Compress
            var quality = 100
            var base64 = ""
            var bytes: ByteArray
            val outputStream = ByteArrayOutputStream()
            
            do {
                outputStream.reset()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                bytes = outputStream.toByteArray()
                quality -= 10
            } while (bytes.size > 135 * 1024 && quality > 10)
            
            base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
            bitmap!!.recycle()
            
            base64
        } catch (e: OutOfMemoryError) {
            null
        } catch (e: Exception) {
            null
        }
    }
}
