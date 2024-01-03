package software.momento.kotlin.sdk.responses.topic

import software.momento.kotlin.sdk.exceptions.SdkException

/**
 * Response for a topic publish operation
 */
public sealed interface TopicPublishResponse {

    /**
     * A successful topic publish operation.
     */
    public object Success : TopicPublishResponse

    /**
     * A failed topic publish operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a copy of the message of the cause.
     */
    public class Error(cause: SdkException): SdkException(cause), TopicPublishResponse
}
