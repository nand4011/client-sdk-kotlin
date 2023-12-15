package software.momento.kotlin.sdk.internal.utils

/**
 * Expect function for decoding base64 encoded string. Required because android and JVM use different Base64 classes
 * before Android API 26.
 * @param encoded A base64 encoded string.
 * @return A decoded string or null if the string is not valid base64.
 */
internal expect fun decodeBase64(encoded: String): String?

/**
 * String extension function for decoding base64 encoded string.
 * @return The decoded string or null if the string is not valid base64.
 */
internal fun String.decodeBase64(): String? = decodeBase64(this)

/**
 * Expect function for encoding a byte array to base64 encoded string. Required because android and JVM use different
 * Base64 classes before Android API 26.
 * @param bytes A byte array to encode.
 * @return A base64 encoded string.
 */
internal expect fun encodeBase64(bytes: ByteArray): String

/**
 * ByteArray extension function for encoding a byte array to base64 encoded string.
 * @return A base64 encoded string.
 */
internal fun ByteArray.encodeBase64(): String = encodeBase64(this)
