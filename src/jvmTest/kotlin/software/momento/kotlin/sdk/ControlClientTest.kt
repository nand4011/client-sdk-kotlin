package software.momento.kotlin.sdk

import kotlinx.coroutines.test.runTest
import org.junit.Test
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration

class ControlClientTest {

    @Test
    fun testCreateCache() = runTest {

        val controlClient = ControlClient(
            credentialProvider = CredentialProvider.fromEnvVar("TEST_API_KEY"),
            configuration = Configuration.Companion.Laptop.Latest
        )

        controlClient.createCache("cacheKotlin")
    }
}