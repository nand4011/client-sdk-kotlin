package software.momento.kotlin.sdk.internal.utils

import java.nio.charset.StandardCharsets
import android.util.Base64

internal actual fun decodeBase64(encoded: String): String? {
    return try {
        val decoded = Base64.decode(encoded, Base64.DEFAULT)
        String(decoded, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}

internal actual fun encodeBase64(bytes: ByteArray): String {
    return Base64.encode(bytes, Base64.DEFAULT).toString(StandardCharsets.UTF_8)
}
