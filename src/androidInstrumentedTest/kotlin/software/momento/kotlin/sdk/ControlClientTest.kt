package software.momento.kotlin.sdk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration

@RunWith(AndroidJUnit4::class)
@LargeTest
class ControlClientTest {

    @Test
    fun testCreateCache() = runTest {

        val arguments = InstrumentationRegistry.getArguments()
        val apiKey = arguments.getString("TestApiKey")!!

        val controlClient = ControlClient(
            credentialProvider = CredentialProvider.fromString(apiKey),
            configuration = Configuration.Companion.Laptop.Latest
        )

        // todo: assert response, tested in console
        controlClient.createCache("cacheAndroid")
    }
}