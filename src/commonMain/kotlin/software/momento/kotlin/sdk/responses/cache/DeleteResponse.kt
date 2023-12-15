package software.momento.kotlin.sdk.responses.cache

import software.momento.kotlin.sdk.exceptions.SdkException

/**
 * Response for a cache delete operation
 */
public sealed interface DeleteResponse {

    /**
     * A successful delete operation.
     */
    public object Success : DeleteResponse

    /**
     * A failed delete operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a
     * copy of the message of the cause.
     */
    public class Error(cause: SdkException): SdkException(cause), DeleteResponse
}
