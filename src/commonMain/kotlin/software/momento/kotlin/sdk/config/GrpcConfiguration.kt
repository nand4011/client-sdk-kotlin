package software.momento.kotlin.sdk.config

import kotlin.time.Duration

/**
 * Configuration for the low-level gRPC settings for communicating with the Momento server.
 * @param timeout The maximum duration of a gRPC call.
 */
public data class GrpcConfiguration(val timeout: Duration)
