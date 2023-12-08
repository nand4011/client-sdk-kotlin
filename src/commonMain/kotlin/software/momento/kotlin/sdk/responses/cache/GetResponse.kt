package software.momento.kotlin.sdk.responses.cache

import software.momento.kotlin.sdk.exceptions.SdkException
import java.nio.charset.StandardCharsets
import java.util.Base64

// Sealed interface for the GetResponse
public sealed interface GetResponse {

    // Represents a successful get operation with a value
    public class Hit(private val value: ByteArray) : GetResponse {
        // Convert value to a string
        private fun valueString(): String = value.toString(StandardCharsets.UTF_8)

        // Return the value as a byte array
        private fun valueByteArray(): ByteArray = value

        // String representation of the Hit class
        override fun toString(): String {
            val valueStringTruncated = valueString()
            // TODO: add encoding for valueByteArray compatible with android and jvm
            return "Hit(valueString=\"$valueStringTruncated\")"
        }
    }

    // Represents a successful get operation with no value
    public object Miss : GetResponse

    // Represents a failed get operation
    public class Error(cause: SdkException) : SdkException(cause), GetResponse
}
