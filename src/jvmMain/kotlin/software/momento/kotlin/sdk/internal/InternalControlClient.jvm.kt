package software.momento.kotlin.sdk.internal

import grpc.control_client._CreateCacheRequest
import io.grpc.Metadata
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import java.io.Closeable

internal actual class InternalControlClient actual constructor(
    credentialProvider: CredentialProvider,
    configuration: Configuration
): Closeable {
    private val stubsManager: ControlGrpcStubsManager

    init {
        stubsManager = ControlGrpcStubsManager(credentialProvider)
    }

    internal actual suspend fun createCache(
        cacheName: String
    ) {
        val metadata = InternalDataClient.metadataWithCache(cacheName)
        val request = _CreateCacheRequest.newBuilder()
                            .setCacheName(cacheName).build()

        // todo: wire response
        this.stubsManager.stub.createCache(request, metadata)
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
