package software.momento.kotlin.sdk.internal

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import java.io.Closeable

internal expect class InternalControlClient(credentialProvider: CredentialProvider, configuration: Configuration) : Closeable {
    internal suspend fun createCache(cacheName: String)


}