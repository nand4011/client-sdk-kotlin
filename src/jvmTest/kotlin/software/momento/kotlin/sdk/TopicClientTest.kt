package software.momento.kotlin.sdk

import kotlinx.coroutines.test.runTest
import org.junit.Test
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.TopicConfigurations
import software.momento.kotlin.sdk.responses.topic.TopicPublishResponse


class TopicClientTest {

    @Test
    fun testPublish() = runTest {
        val topicClient = TopicClient(
            credentialProvider = CredentialProvider.fromEnvVar("TEST_API_KEY"),
            configuration = TopicConfigurations.Laptop.Latest
        )
        val response = topicClient.publish("cache", "topic", "jvm!")
        if (response is TopicPublishResponse.Error) {
            println(response.cause)
        }
        assert(response is TopicPublishResponse.Success)
    }
}
