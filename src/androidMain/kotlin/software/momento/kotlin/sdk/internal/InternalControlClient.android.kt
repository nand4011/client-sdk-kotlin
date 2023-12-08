package software.momento.kotlin.sdk.internal

import grpc.control_client._CreateCacheRequest
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import java.io.Closeable
import kotlin.time.Duration

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

    override fun close() {
        stubsManager.close()
    }
}
