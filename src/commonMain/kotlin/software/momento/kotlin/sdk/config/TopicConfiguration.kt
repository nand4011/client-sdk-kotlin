package software.momento.kotlin.sdk.config

import kotlin.time.Duration

/**
 * The container for Topic configurables.
 * @param transportStrategy Configuration for network tunables.
 */
public data class TopicConfiguration(val transportStrategy: TopicTransportStrategy) {

    /**
     * Creates a new topic configuration with the specified gRPC timeout.
     */
    public fun withTimeout(timeout: Duration): TopicConfiguration {
        val updatedGrpcConfig = transportStrategy.grpcConfiguration.copy(timeout = timeout)
        val updatedTransportStrategy = transportStrategy.copy(grpcConfiguration = updatedGrpcConfig)
        return copy(transportStrategy = updatedTransportStrategy)
    }
}

/**
 * Configuration for network settings for communicating with the Momento server.
 * @param grpcConfiguration Configuration for gRPC tunables.
 */
public data class TopicTransportStrategy(val grpcConfiguration: GrpcConfiguration)
