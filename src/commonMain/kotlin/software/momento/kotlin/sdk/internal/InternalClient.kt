package software.momento.kotlin.sdk.internal

import io.grpc.Metadata

internal abstract class InternalClient: AutoCloseable {
    companion object {
        private val CACHE_NAME_KEY = Metadata.Key.of("cache", Metadata.ASCII_STRING_MARSHALLER)

        fun metadataWithCache(cacheName: String): Metadata {
            val metadata = Metadata()
            metadata.put(CACHE_NAME_KEY, cacheName)
            return metadata
        }
    }
}
