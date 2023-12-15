package software.momento.kotlin.sdk.internal

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.DeleteResponse
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import kotlin.time.Duration

internal expect class InternalDataClient(
    credentialProvider: CredentialProvider,
    configuration: Configuration,
    itemDefaultTtl: Duration
) : InternalClient {

    internal suspend fun set(cacheName: String, key: String, value: String, ttl: Duration? = null): SetResponse

    internal suspend fun set(cacheName: String, key: ByteArray, value: String, ttl: Duration? = null): SetResponse

    internal suspend fun set(cacheName: String, key: String, value: ByteArray, ttl: Duration? = null): SetResponse

    internal suspend fun set(cacheName: String, key: ByteArray, value: ByteArray, ttl: Duration? = null): SetResponse

    internal suspend fun get(cacheName: String, key: String): GetResponse

    internal suspend fun get(cacheName: String, key: ByteArray): GetResponse

    internal suspend fun delete(cacheName: String, key: String): DeleteResponse

    internal suspend fun delete(cacheName: String, key: ByteArray): DeleteResponse
}
