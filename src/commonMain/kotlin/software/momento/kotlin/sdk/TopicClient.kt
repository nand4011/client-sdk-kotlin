package software.momento.kotlin.sdk

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfiguration
import software.momento.kotlin.sdk.internal.InternalTopicClient
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse
import software.momento.kotlin.sdk.responses.topic.TopicSubscribeResponse
import java.io.Closeable

/**
 * Client to perform operations against a Momento Topic.
 * @param credentialProvider The provider for the credentials required to connect to Momento.
 * @param configuration The configuration object containing all tunable client settings.
 */
public class TopicClient(credentialProvider: CredentialProvider, configuration: TopicConfiguration): Closeable {
    private val topicClient: InternalTopicClient

    init {
        topicClient = InternalTopicClient(credentialProvider, configuration)
    }

    /**
     * Publishes a message to a topic.
     *
     * @param cacheName The name of the cache associated with the topic.
     * @param topicName The name of the topic to publish to.
     * @param value The value to publish to the topic.
     * @return The result of the publish operation: [TopicPublishResponse.Success] or [CacheCreateResponse.Error].
     */
    public suspend fun publish(cacheName: String, topicName: String, value: String): TopicPublishResponse {
        return topicClient.publish(cacheName, topicName, value)
    }

    /**
     * Publishes a message to a topic.
     *
     * @param cacheName The name of the cache associated with the topic.
     * @param topicName The name of the topic to publish to.
     * @param value The value to publish to the topic.
     * @return The result of the publish operation: [TopicPublishResponse.Success] or [CacheCreateResponse.Error].
     */
    public suspend fun publish(cacheName: String, topicName: String, value: ByteArray): TopicPublishResponse {
        return topicClient.publish(cacheName, topicName, value)
    }

    /**
     * Subscribes to a topic.
     *
     * @param cacheName The name of the cache associated with the topic.
     * @param topicName The name of the topic to subscribe to.
     * @return The result of the subscribe operation: [TopicSubscribeResponse.Subscription], a flow of topic messages;
     * or [TopicSubscribeResponse.Error].
     */
    public suspend fun subscribe(cacheName: String, topicName: String): TopicSubscribeResponse {
        return topicClient.subscribe(cacheName, topicName)
    }

    override fun close() {
        topicClient.close()
    }
}
