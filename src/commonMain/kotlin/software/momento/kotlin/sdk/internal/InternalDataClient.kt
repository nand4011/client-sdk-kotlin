package software.momento.kotlin.sdk.internal

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import java.io.Closeable

internal expect class InternalDataClient(credentialProvider: CredentialProvider, configuration: Configuration) : Closeable {

    internal suspend fun set(cacheName: String, key: String, value: String, ttlMilliSeconds: Long): SetResponse
    internal suspend fun get(cacheName: String, key: String): GetResponse
}