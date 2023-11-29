package software.momento.kotlin.sdk.auth

import java.nio.charset.StandardCharsets
import java.util.Base64

public actual fun decodeBase64(encoded: String): String? {
    return try {
        @Suppress("NewApi") // The android implementation uses android.util.Base64
        val decoded = Base64.getDecoder().decode(encoded)
        String(decoded, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}
