package software.momento.kotlin.sdk

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfiguration
import software.momento.kotlin.sdk.internal.InternalTopicClient
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse
import java.io.Closeable

public class TopicClient(credentialProvider: CredentialProvider, configuration: TopicConfiguration): Closeable {
    private val internalTopicClient: InternalTopicClient

    init {
        internalTopicClient = InternalTopicClient(credentialProvider, configuration)
    }

    public suspend fun publish(cacheName: String, topicName: String, value: String): TopicPublishResponse {
        return internalTopicClient.publish(cacheName, topicName, value)
    }

    override fun close() {
        internalTopicClient.close()
    }
}
