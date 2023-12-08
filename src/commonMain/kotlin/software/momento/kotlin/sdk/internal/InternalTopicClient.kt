package software.momento.kotlin.sdk.internal

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfiguration
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse
import java.io.Closeable

internal expect class InternalTopicClient(credentialProvider: CredentialProvider, configuration: TopicConfiguration) :
    Closeable {

    internal suspend fun publish(cacheName: String, topicName: String, value: String): TopicPublishResponse
}
