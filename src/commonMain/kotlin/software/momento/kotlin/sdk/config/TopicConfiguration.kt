package software.momento.kotlin.sdk.config

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * The container for Topic configurables.
 * @param transportStrategy Configuration for network tunables.
 */
public data class TopicConfiguration(val transportStrategy: TopicTransportStrategy) {

    /**
     * Prebuilt configurations for different environments.
     */
    public companion object {
        /**
         * Provides defaults suitable for a medium-to-high-latency dev environment. Permissive timeouts
         * and relaxed latency and throughput targets.
         */
        public object Laptop {
            /**
             * Provides the latest recommended configuration for a dev environment.
             *
             * <p>This configuration may change in future releases to take advantage of improvements we
             * identify for default configurations.
             *
             * @return the latest Laptop configuration
             */
            public val Latest: TopicConfiguration = TopicConfiguration(
                transportStrategy = TopicTransportStrategy(
                    grpcConfiguration = GrpcConfiguration(
                        timeout = 15.seconds
                    )
                )
            )
        }

        /**
         * Provides defaults suitable for a medium-to-high-latency mobile environment. Permissive timeouts
         * and relaxed latency and throughput targets.
         */
        public object Mobile {
            /**
             * Provides the latest recommended configuration for a mobile environment.
             *
             * <p>This configuration may change in future releases to take advantage of improvements we
             * identify for default configurations.
             *
             * @return the latest Mobile configuration
             */
            public val Latest: TopicConfiguration = TopicConfiguration(
                transportStrategy = TopicTransportStrategy(
                    grpcConfiguration = GrpcConfiguration(
                        timeout = 5.seconds
                    )
                )
            )
        }

        /**
         * Provides defaults suitable for an environment where your client is running in the same region
         * as the Momento service. It has more aggressive timeouts than the Laptop config.
         */
        public object InRegion {
            /**
             * Provides the latest recommended configuration for an in-region environment.
             *
             * <p>This configuration may change in future releases to take advantage of improvements we
             * identify for default configurations.
             *
             * @return the latest in-region configuration
             */
            public val Latest: TopicConfiguration = TopicConfiguration(
                transportStrategy = TopicTransportStrategy(
                    grpcConfiguration = GrpcConfiguration(
                        timeout = 1100.milliseconds
                    )
                )
            )

            /**
             * This config prioritizes keeping p99.9 latencies as low as possible, potentially sacrificing
             * some throughput to achieve this. Use this configuration if low latency is more important in
             * your application than cache availability.
             */
            public object LowLatency {
                /**
                 * Provides the latest recommended configuration for a low-latency in-region environment.
                 *
                 * <p>This configuration may change in future releases to take advantage of improvements we
                 * identify for default configurations.
                 *
                 * @return the latest low-latency configuration
                 */
                public val Latest: TopicConfiguration = TopicConfiguration(
                    transportStrategy = TopicTransportStrategy(
                        grpcConfiguration = GrpcConfiguration(
                            timeout = 500.milliseconds
                        )
                    )
                )
            }
        }
    }

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
