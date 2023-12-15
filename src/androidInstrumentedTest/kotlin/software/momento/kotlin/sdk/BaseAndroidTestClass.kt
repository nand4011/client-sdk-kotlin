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

        lateinit var cacheClient: CacheClient

        @JvmStatic
        @BeforeClass
        fun setUp() {
            val arguments = InstrumentationRegistry.getArguments()
            val apiKey = arguments.getString("TestApiKey")!!
            cacheClient = CacheClient(
                credentialProvider = CredentialProvider.fromString(apiKey),
                configuration = Configurations.Mobile.latest,
                itemDefaultTtl = 60.seconds
            )

            runBlocking { cacheClient.createCache(cacheName) }
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            runBlocking { cacheClient.deleteCache(cacheName) }
            cacheClient.close()
        }
    }
}
