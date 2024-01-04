package software.momento.kotlin.sdk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import software.momento.kotlin.sdk.exceptions.NotFoundException
import software.momento.kotlin.sdk.responses.cache.control.CacheCreateResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheDeleteResponse
import software.momento.kotlin.sdk.responses.cache.control.CacheListResponse
import java.util.*
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class CacheClientControlTest: BaseAndroidTestClass() {

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
        val cacheName = "kotlin-android-create-delete-${UUID.randomUUID()}"

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
        val cacheName = "kotlin-android-create-delete-${UUID.randomUUID()}"
        var createResponse = cacheClient.createCache(cacheName)
        assert(createResponse is CacheCreateResponse.Success)

        var listCachesResponse = cacheClient.listCaches()
        assert(listCachesResponse is CacheListResponse.Success)

        val caches = (listCachesResponse as CacheListResponse.Success).getCaches()
        val cacheNames = caches.map { it.name() }
        assert(cacheNames.contains(cacheName))
    }
}
