package software.momento.kotlin.sdk.internal.utils

import java.nio.charset.StandardCharsets
import java.util.Base64

internal actual fun decodeBase64(encoded: String): String? {
    return try {
        @Suppress("NewApi") // The android implementation uses android.util.Base64
        val decoded = Base64.getDecoder().decode(encoded)
        String(decoded, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}

internal actual fun encodeBase64(bytes: ByteArray): String {
    @Suppress("NewApi") // The android implementation uses android.util.Base64
    return Base64.getEncoder().encodeToString(bytes)
}
