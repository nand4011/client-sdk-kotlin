package software.momento.kotlin.sdk.responses.cache.control

import software.momento.kotlin.sdk.exceptions.SdkException

/**
 * Response for a delete cache operation.
 */
public sealed interface CacheDeleteResponse {

    /** A successful delete cache operation. */
    public object Success : CacheDeleteResponse

    /**
     * A failed delete cache operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a
     * copy of the message of the cause.
     */
    public class Error(cause: SdkException) : SdkException(cause), CacheDeleteResponse
}
