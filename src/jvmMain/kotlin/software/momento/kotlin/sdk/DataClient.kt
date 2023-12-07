package software.momento.kotlin.sdk

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.internal.InternalDataClient
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import java.io.Closeable

public class DataClient(credentialProvider: CredentialProvider, configuration: Configuration): Closeable {

    private val internalDataClient: InternalDataClient

    init {
        internalDataClient = InternalDataClient(credentialProvider, configuration)
    }

    public suspend fun set(cacheName: String, key: String, value: String, ttlMilliseconds: Long) : SetResponse {
        return internalDataClient.set(cacheName, key, value, ttlMilliseconds);
    }

    public suspend fun get(cacheName: String, key: String) : GetResponse {
        return internalDataClient.get(cacheName, key);
    }
c
    override fun close() {
        internalDataClient.close()
    }


}