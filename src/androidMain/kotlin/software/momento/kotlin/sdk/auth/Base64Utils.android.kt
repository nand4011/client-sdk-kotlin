package software.momento.kotlin.sdk.auth

import java.nio.charset.StandardCharsets
import android.util.Base64

public actual fun decodeBase64(encoded: String): String? {
    return try {
        val decoded = Base64.decode(encoded, Base64.DEFAULT)
        String(decoded, StandardCharsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}
