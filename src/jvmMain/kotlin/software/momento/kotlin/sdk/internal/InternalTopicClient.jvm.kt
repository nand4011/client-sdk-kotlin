package software.momento.kotlin.sdk.internal

import grpc.cache_client.pubsub._PublishRequest
import grpc.cache_client.pubsub._TopicValue
import io.grpc.Metadata
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfiguration
import software.momento.kotlin.sdk.exceptions.CacheServiceExceptionMapper
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse
import java.io.Closeable

internal actual class InternalTopicClient actual constructor(
    credentialProvider: CredentialProvider,
    configuration: TopicConfiguration
): Closeable {
    private val stubsManager: TopicGrpcStubsManager

    init {
        stubsManager = TopicGrpcStubsManager(credentialProvider)
    }

    internal actual suspend fun publish(
        cacheName: String, topicName: String, value: String
    ): TopicPublishResponse {
        val metadata = metadataWithCache(cacheName)

        val request = _PublishRequest.newBuilder()
            .setCacheName(cacheName)
            .setTopic(topicName)
            .setValue(_TopicValue.newBuilder().setText(value).build())
            .build()

        try {
            stubsManager.stub.publish(request, metadata)
        } catch (e: Exception) {
            return TopicPublishResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        }

        return TopicPublishResponse.Success
    }

    companion object {
        private val CACHE_NAME_KEY = Metadata.Key.of("cache", Metadata.ASCII_STRING_MARSHALLER)

        fun metadataWithCache(cacheName: String): Metadata {
            val metadata = Metadata()
            metadata.put(CACHE_NAME_KEY, cacheName)
            return metadata
        }
    }

    override fun close() {
        stubsManager.close()
    }
}
