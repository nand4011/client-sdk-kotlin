package software.momento.kotlin.sdk.auth

/**
 * Expect function for decoding base64 encoded string. Required because android and JVM use different Base64 classes
 * before Android API 26.
 * @param encoded A base64 encoded string.
 * @return A decoded string or null if the string is not valid base64.
 */
public expect fun decodeBase64(encoded: String): String?

/**
 * String extension function for decoding base64 encoded string.
 * @return The decoded string or null if the string is not valid base64.
 */
public fun String.decodeBase64(): String? = decodeBase64(this)
