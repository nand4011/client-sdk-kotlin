package software.momento.kotlin.sdk.responses.cache.control

import software.momento.kotlin.sdk.exceptions.SdkException

/** Response for a create cache operation in Kotlin */
public sealed interface CacheCreateResponse {

    /** A successful create cache operation that created a cache. */
    public object Success : CacheCreateResponse

    /** A no-op create cache operation that occurred because the cache already existed. */
    public object AlreadyExists : CacheCreateResponse

    /**
     * A failed create cache operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a
     * copy of the message of the cause.
     */
    public class Error(cause: SdkException) : SdkException(cause), CacheCreateResponse
}
