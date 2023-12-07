package software.momento.kotlin.sdk.internal

import io.grpc.Status
import io.grpc.Metadata

/**
 * Captures gRPC-level information about an error.
 */
public data class MomentoGrpcErrorDetails(
    public val statusCode: Status.Code,
    public val details: String,
    public val metadata: Metadata? = null
) {
    /**
     * Returns the cache name from the metadata if present.
     */
    public fun getCacheName(): String? = metadata?.get(Metadata.Key.of("cache", Metadata.ASCII_STRING_MARSHALLER))
}
