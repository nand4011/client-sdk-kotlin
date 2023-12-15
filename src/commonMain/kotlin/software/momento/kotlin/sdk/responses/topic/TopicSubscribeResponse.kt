package software.momento.kotlin.sdk.responses.topic

import kotlinx.coroutines.flow.Flow
import software.momento.kotlin.sdk.exceptions.SdkException


/**
 * Response for a topic subscribe operation
 */
public sealed interface TopicSubscribeResponse {

    /**
     * A successful topic subscribe operation. The response is a flow of [TopicMessage]s representing the incoming
     * messages on the topic.
     */
    public class Subscription(private val flow: Flow<TopicMessage>) : Flow<TopicMessage> by flow, TopicSubscribeResponse

    /**
     * A failed topic subscribe operation. The response itself is an exception, so it can be directly
     * thrown, or the cause of the error can be retrieved. The message is a copy of the message of the cause.
     */
    public class Error(cause: SdkException) : SdkException(cause), TopicSubscribeResponse
}
