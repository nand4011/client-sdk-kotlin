package software.momento.kotlin.sdk.internal

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheDeleteResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheListResponse

internal expect class InternalControlClient(credentialProvider: CredentialProvider, configuration: Configuration) :
    InternalClient {

    internal suspend fun createCache(cacheName: String): CacheCreateResponse

    internal suspend fun deleteCache(cacheName: String): CacheDeleteResponse

    internal suspend fun listCaches(): CacheListResponse
}
