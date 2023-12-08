package software.momento.kotlin.sdk

import kotlinx.coroutines.test.runTest
import org.junit.Test
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import kotlin.test.fail
import kotlin.time.Duration.Companion.seconds

class DataClientTest {

    @Test
    fun testSetAndGet() = runTest {

        val dataClient = DataClient(
            credentialProvider = CredentialProvider.fromEnvVar("TEST_API_KEY"),
            configuration = Configuration.Companion.Laptop.Latest
        )

        val setResponse = dataClient.set("cache", "keyKotlin", "val", 60.seconds)
        if (setResponse is SetResponse.Error) {
            fail(setResponse.cause?.message)
        }
        assert(setResponse is SetResponse.Success)

        val getResponse = dataClient.get("cache", "keyKotlin")
        if (getResponse is GetResponse.Error) {
            fail(getResponse.cause?.message)
        }
        assert(getResponse is GetResponse.Hit)

        val getResponseMiss = dataClient.get("cache", "keyKotlinNotExisting")
        if (getResponseMiss is GetResponse.Error) {
            fail(getResponseMiss.cause?.message)
        }
        assert(getResponseMiss is GetResponse.Miss)
    }
}