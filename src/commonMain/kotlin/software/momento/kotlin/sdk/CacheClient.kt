package software.momento.kotlin.sdk

import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.internal.InternalControlClient
import software.momento.kotlin.sdk.internal.InternalDataClient
import software.momento.kotlin.sdk.responses.cache.DeleteResponse
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheDeleteResponse
import kotlin.time.Duration

/**
 * Client to perform operations against a Momento cache.
 * @param credentialProvider The provider for the credentials required to connect to Momento.
 * @param configuration The configuration object containing all tunable client settings.
 * @param itemDefaultTtl The default TTL for values written to a cache.
 */
public class CacheClient(
    credentialProvider: CredentialProvider,
    configuration: Configuration,
    itemDefaultTtl: Duration
) : AutoCloseable {

    private val dataClient: InternalDataClient
    private val controlClient: InternalControlClient

    init {
        dataClient = InternalDataClient(credentialProvider, configuration, itemDefaultTtl)
        controlClient = InternalControlClient(credentialProvider, configuration)
    }

    /**
     * Asynchronously creates a cache with the provided name.
     *
     * @param cacheName The name of the cache to be created.
     * @return The result of the cache creation: [CacheCreateResponse.Success] or [CacheCreateResponse.Error].
     */
    public suspend fun createCache(cacheName: String): CacheCreateResponse {
        return controlClient.createCache(cacheName)
    }

    /**
     * Asynchronously deletes a cache.
     *
     * @param cacheName The name of the cache to be deleted.
     * @return The result of the cache deletion: [CacheDeleteResponse.Success] or [CacheDeleteResponse.Error].
     */
    public suspend fun deleteCache(cacheName: String): CacheDeleteResponse {
        return controlClient.deleteCache(cacheName)
    }

    /**
     * Asynchronously sets the value in cache with a given Time To Live (TTL) seconds.
     *
     * <p>If a value for this key is already present it will be replaced by the new value.
     *
     * @param cacheName The name of the cache to store the item in.
     * @param key The key under which the value is to be added.
     * @param value The value to be stored.
     * @param ttl The time to Live for the item in the cache. This TTL takes precedence over the TTL used when
     *     creating a cache client.
     * @return The result of the set operation: [SetResponse.Success] or [SetResponse.Error].
     */
    public suspend fun set(cacheName: String, key: String, value: String, ttl: Duration? = null) : SetResponse {
        return dataClient.set(cacheName, key, value, ttl)
    }

    /**
     * Asynchronously sets the value in cache with a given Time To Live (TTL) seconds.
     *
     * <p>If a value for this key is already present it will be replaced by the new value.
     *
     * @param cacheName The name of the cache to store the item in.
     * @param key The key under which the value is to be added.
     * @param value The value to be stored.
     * @param ttl The time to Live for the item in the cache. This TTL takes precedence over the TTL used when
     *     creating a cache client.
     * @return The result of the set operation: [SetResponse.Success] or [SetResponse.Error].
     */
    public suspend fun set(cacheName: String, key: ByteArray, value: String, ttl: Duration? = null) : SetResponse {
        return dataClient.set(cacheName, key, value, ttl)
    }

    /**
     * Asynchronously sets the value in cache with a given Time To Live (TTL) seconds.
     *
     * <p>If a value for this key is already present it will be replaced by the new value.
     *
     * @param cacheName The name of the cache to store the item in.
     * @param key The key under which the value is to be added.
     * @param value The value to be stored.
     * @param ttl The time to Live for the item in the cache. This TTL takes precedence over the TTL used when
     *     creating a cache client.
     * @return The result of the set operation: [SetResponse.Success] or [SetResponse.Error].
     */
    public suspend fun set(cacheName: String, key: String, value: ByteArray, ttl: Duration? = null) : SetResponse {
        return dataClient.set(cacheName, key, value, ttl)
    }

    /**
     * Asynchronously sets the value in cache with a given Time To Live (TTL) seconds.
     *
     * <p>If a value for this key is already present it will be replaced by the new value.
     *
     * @param cacheName The name of the cache to store the item in.
     * @param key The key under which the value is to be added.
     * @param value The value to be stored.
     * @param ttl The time to Live for the item in the cache. This TTL takes precedence over the TTL used when
     *     creating a cache client.
     * @return The result of the set operation: [SetResponse.Success] or [SetResponse.Error].
     */
    public suspend fun set(cacheName: String, key: ByteArray, value: ByteArray, ttl: Duration? = null) : SetResponse {
        return dataClient.set(cacheName, key, value, ttl)
    }

    /**
     * Asynchronously gets the cache value for the given key.
     *
     * @param cacheName The name of the cache to get the item from.
     * @param key The key to get.
     * @return The result of the get operation: [GetResponse.Hit] containing the value if an item is found,
     * [GetResponse.Miss] if the item is not found, or [GetResponse.Error].
     */
    public suspend fun get(cacheName: String, key: String) : GetResponse {
        return dataClient.get(cacheName, key)
    }

    /**
     * Asynchronously gets the cache value for the given key.
     *
     * @param cacheName The name of the cache to get the item from.
     * @param key The key to get.
     * @return The result of the get operation: [GetResponse.Hit] containing the value if an item is found,
     * [GetResponse.Miss] if the item is not found, or [GetResponse.Error].
     */
    public suspend fun get(cacheName: String, key: ByteArray) : GetResponse {
        return dataClient.get(cacheName, key)
    }

    /**
     * Asynchronously deletes the cache value for the given key.
     *
     * @param cacheName The name of the cache to delete the item in.
     * @param key The key to delete.
     * @return The result of the set operation: [DeleteResponse.Success] or [DeleteResponse.Error].
     */
    public suspend fun delete(cacheName: String, key: String) : DeleteResponse {
        return dataClient.delete(cacheName, key)
    }

    /**
     * Asynchronously deletes the cache value for the given key.
     *
     * @param cacheName The name of the cache to delete the item in.
     * @param key The key to delete.
     * @return The result of the set operation: [DeleteResponse.Success] or [DeleteResponse.Error].
     */
    public suspend fun delete(cacheName: String, key: ByteArray) : DeleteResponse {
        return dataClient.delete(cacheName, key)
    }

    override fun close() {
        dataClient.close()
        controlClient.close()
    }
}
