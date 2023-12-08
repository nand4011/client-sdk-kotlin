package software.momento.kotlin.sdk

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.internal.InternalControlClient
import software.momento.kotlin.sdk.internal.InternalDataClient
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import java.io.Closeable
import kotlin.time.Duration

public class ControlClient(credentialProvider: CredentialProvider, configuration: Configuration): Closeable {

    private val internalControlClient: InternalControlClient

    init {
        internalControlClient = InternalControlClient(credentialProvider, configuration)
    }

    public suspend fun createCache(cacheName: String) {
        return internalControlClient.createCache(cacheName)
    }

    override fun close() {
        internalControlClient.close()
    }

}