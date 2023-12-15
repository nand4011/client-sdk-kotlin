package software.momento.kotlin.sdk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import software.momento.kotlin.sdk.exceptions.InvalidArgumentException
import software.momento.kotlin.sdk.responses.cache.DeleteResponse
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse

@RunWith(AndroidJUnit4::class)
@LargeTest
class CacheClientScalarTest: BaseAndroidTestClass() {

    @Test
    fun getFailsWithInvalidCacheName() = runTest {
        val response = cacheClient.get("", "key")
        assert((response as GetResponse.Error).cause is InvalidArgumentException)
    }

    @Test
    fun setFailsWithInvalidCacheName() = runTest {
        val response = cacheClient.set("", "key", "value")
        assert((response as SetResponse.Error).cause is InvalidArgumentException)
    }

    @Test
    fun deleteFailsWithInvalidCacheName() = runTest {
        val response = cacheClient.delete("", "key")
        assert((response as DeleteResponse.Error).cause is InvalidArgumentException)
    }

    @Test
    fun getSetDeleteHappyPath_String() = runTest {
        val key = "cache-key"
        val value = "cache-value"

        var getResponse = cacheClient.get(cacheName, key)
        assert(getResponse is GetResponse.Miss)

        val setResponse = cacheClient.set(cacheName, key, value)
        assert(setResponse is SetResponse.Success)

        getResponse = cacheClient.get(cacheName, key)
        assert((getResponse as GetResponse.Hit).value == value)

        val deleteResponse = cacheClient.delete(cacheName, key)
        assert(deleteResponse is DeleteResponse.Success)

        getResponse = cacheClient.get(cacheName, key)
        assert(getResponse is GetResponse.Miss)
    }

    @Test
    fun getSetDeleteHappyPath_Bytes() = runTest {
        val key = "cache-key".encodeToByteArray()
        val value = "cache-value".encodeToByteArray()

        var getResponse = cacheClient.get(cacheName, key)
        assert(getResponse is GetResponse.Miss)

        val setResponse = cacheClient.set(cacheName, key, value)
        assert(setResponse is SetResponse.Success)

        getResponse = cacheClient.get(cacheName, key)
        assert((getResponse as GetResponse.Hit).valueByteArray.contentEquals(value))

        val deleteResponse = cacheClient.delete(cacheName, key)
        assert(deleteResponse is DeleteResponse.Success)

        getResponse = cacheClient.get(cacheName, key)
        assert(getResponse is GetResponse.Miss)
    }
}
