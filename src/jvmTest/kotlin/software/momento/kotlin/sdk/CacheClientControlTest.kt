package software.momento.kotlin.sdk

import kotlinx.coroutines.test.runTest
import software.momento.kotlin.sdk.exceptions.NotFoundException
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheDeleteResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheListResponse
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CacheClientControlTest: BaseJvmTestClass() {

    @Test
    fun createCacheFailsWithInvalidCacheName() = runTest {
        val response = cacheClient.createCache("")
        assert(response is CacheCreateResponse.Error)
    }

    @Test
    fun deleteCacheFailsWithInvalidCacheName() = runTest {
        val response = cacheClient.deleteCache("")
        assert(response is CacheDeleteResponse.Error)
    }

    @Test
    fun createDeleteCacheHappyPath() = runTest {
        val cacheName = "kotlin-jvm-create-delete-${UUID.randomUUID()}"

        try {
            var createResponse = cacheClient.createCache(cacheName)
            assert(createResponse is CacheCreateResponse.Success)

            createResponse = cacheClient.createCache(cacheName)
            assert(createResponse is CacheCreateResponse.AlreadyExists)
        } finally {
            var deleteResponse = cacheClient.deleteCache(cacheName)
            assert(deleteResponse is CacheDeleteResponse.Success)

            deleteResponse = cacheClient.deleteCache(cacheName)
            assert((deleteResponse as CacheDeleteResponse.Error).cause is NotFoundException)
        }
    }

    @Test
    fun listCacheHappyPath() = runTest  {
        val cacheName = "kotlin-jvm-create-delete-${UUID.randomUUID()}"
        var createResponse = cacheClient.createCache(cacheName)
        assert(createResponse is CacheCreateResponse.Success)

        var listCachesResponse = cacheClient.listCaches()
        assert(listCachesResponse is CacheListResponse.Success)

        val caches = (listCachesResponse as CacheListResponse.Success).caches
        assertTrue(caches.size > 1, "There should be exactly 2 caches in the response")

        val cacheNames = caches.map { it.name }
        assertTrue(cacheName in cacheNames, "$cacheName should be one of the cache names")

        var deleteResponse = cacheClient.deleteCache(cacheName)
        assert(deleteResponse is CacheDeleteResponse.Success)
    }
}
