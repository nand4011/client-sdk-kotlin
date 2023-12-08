package software.momento.kotlin.sdk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfiguration
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse

@RunWith(AndroidJUnit4::class)
@LargeTest
class TopicClientTest {

    @Test
    fun testPublish() = runTest {
        val arguments = InstrumentationRegistry.getArguments()
        val apiKey = arguments.getString("TestApiKey")!!

        val topicClient = TopicClient(
            credentialProvider = CredentialProvider.fromString(apiKey),
            configuration = TopicConfiguration.Companion.Laptop.Latest
        )
        val response = topicClient.publish("cache", "topic", "android!")
        if (response is TopicPublishResponse.Error) {
            println(response.cause)
        }
        assert(response is TopicPublishResponse.Success)
    }
}
