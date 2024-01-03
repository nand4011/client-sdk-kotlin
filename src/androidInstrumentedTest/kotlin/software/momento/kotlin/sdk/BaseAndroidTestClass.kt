package software.momento.kotlin.sdk

import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configurations
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

open class BaseAndroidTestClass {
    companion object {
        val cacheName: String = System.getenv("TEST_CACHE_NAME") ?: "kotlin-android-integration-${UUID.randomUUID()}"

        @JvmStatic
        protected lateinit var credentialProvider: CredentialProvider
        lateinit var cacheClient: CacheClient

        @JvmStatic
        @BeforeClass
        fun createCacheClient() {
            val arguments = InstrumentationRegistry.getArguments()
            val apiKey = arguments.getString("TestApiKey")!!
            credentialProvider = CredentialProvider.fromString(apiKey)
            cacheClient = CacheClient(
                credentialProvider = credentialProvider,
                configuration = Configurations.Mobile.latest,
                itemDefaultTtl = 60.seconds
            )

            runBlocking { cacheClient.createCache(cacheName) }
        }

        @JvmStatic
        @AfterClass
        fun destroyCacheClient() {
            runBlocking { cacheClient.deleteCache(cacheName) }
            cacheClient.close()
        }
    }
}
