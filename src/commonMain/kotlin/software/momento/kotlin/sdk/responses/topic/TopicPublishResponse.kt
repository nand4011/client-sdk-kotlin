package software.momento.kotlin.sdk.responses.topic

import software.momento.kotlin.sdk.exceptions.SdkException

public sealed interface TopicPublishResponse {

    public object Success : TopicPublishResponse

    public class Error(cause: SdkException): SdkException(cause), TopicPublishResponse
}
