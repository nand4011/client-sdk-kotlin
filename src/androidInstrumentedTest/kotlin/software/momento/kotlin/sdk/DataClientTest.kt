package software.momento.kotlin.sdk

import junit.framework.TestCase.fail
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import kotlin.time.Duration.Companion.seconds

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import software.momento.kotlin.sdk.auth.CredentialProvider

@RunWith(AndroidJUnit4::class)
@LargeTest
class DataClientTest {

    @Test
    fun testSetAndGet() = runTest {
        val arguments = InstrumentationRegistry.getArguments()
        val apiKey = arguments.getString("TestApiKey")!!

        val dataClient = DataClient(
            credentialProvider = CredentialProvider.fromString(apiKey),
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