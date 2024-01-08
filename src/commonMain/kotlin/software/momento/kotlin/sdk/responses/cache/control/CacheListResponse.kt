package software.momento.kotlin.sdk.responses.cache.control

import software.momento.kotlin.sdk.exceptions.SdkException

/** Response for a list caches operation. */
public sealed interface CacheListResponse {

    /** A successful list caches operation. Contains the discovered caches.  */
    public data class Success(val caches: List<CacheInfo>) : CacheListResponse

    /**
     * A failed list caches operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved with [.getCause]. The message is a
     * copy of the message of the cause.
     */
    public class Error(cause: SdkException) : SdkException(cause), CacheListResponse

}