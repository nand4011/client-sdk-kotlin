package software.momento.kotlin.sdk.responses.cache

import software.momento.kotlin.sdk.exceptions.SdkException
import software.momento.kotlin.sdk.internal.utils.encodeBase64
import software.momento.kotlin.sdk.internal.utils.truncate

/**
 * Response for a cache get operation
 */
public sealed interface GetResponse {

    /**
     * A successful get operation for a key that has a value.
     *
     * @property valueByteArray The retrieved value as a byte array.
     */
    public data class Hit(public val valueByteArray: ByteArray) : GetResponse {

        /**
         * The retrieved value of as a string.
         */
        val value: String by lazy {
            String(valueByteArray)
        }

        /**
         * The retrieved value of as a string.
         */
        val valueString: String
            get() = value

        override fun toString(): String {
            val valueStringTruncated = value.truncate(20)
            val valueByteArrayTruncated = valueByteArray.encodeBase64().truncate(20)
            return "Hit(valueString=\"$valueStringTruncated\", valueByteArray=\"$valueByteArrayTruncated\")"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Hit

            return valueByteArray.contentEquals(other.valueByteArray)
        }

        override fun hashCode(): Int {
            return valueByteArray.contentHashCode()
        }
    }

    /**
     * A successful get operation for a key that has no value.
     */
    public object Miss : GetResponse

    /**
     * A failed get operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a
     * copy of the message of the cause.
     */
    public class Error(cause: SdkException) : SdkException(cause), GetResponse
}
