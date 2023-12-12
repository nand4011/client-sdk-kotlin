package software.momento.kotlin.sdk.internal.utils

import com.google.protobuf.ByteString

/**
 * Utility functions for interacting with [ByteString].
 */
internal object ByteStringExtensions {

    /**
     * Converts a [String] to a [ByteString].
     */
    internal fun String.toByteString(): ByteString = ByteString.copyFromUtf8(this)

    /**
     * Converts a [ByteArray] to a [ByteString].
     */
    internal fun ByteArray.toByteString(): ByteString = ByteString.copyFrom(this)
}
