package software.momento.kotlin.sdk.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Contains the information required for a Momento client to connect to and authenticate with
 * the Momento service.
 */
public data class CredentialProvider(
    val controlEndpoint: String, val cacheEndpoint: String, val apiKey: String
) {
    public companion object {
        //TODO: throw Momento exceptions when those are added

        /**
         * Creates a [CredentialProvider] from a Momento API key.
         * @param apiKey The Momento API key to use for authentication.
         * @param controlHost An optional override for the endpoint used for control operations.
         * @param cacheHost An optional override for the endpoint used for cache operations.
         */
        public fun fromString(
            apiKey: String, controlHost: String? = null, cacheHost: String? = null
        ): CredentialProvider {
            try {
                val provider = try {
                    processLegacyKey(apiKey)
                } catch (e: Exception) {
                    processV1Key(apiKey)
                }
                return provider.copy(
                    controlEndpoint = controlHost ?: provider.controlEndpoint,
                    cacheEndpoint = cacheHost ?: provider.cacheEndpoint
                )
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid API key")
            }
        }

        /**
         * Creates a [CredentialProvider] from a Momento API key stored in an environment variable.
         * @param envVar The name of the environment variable containing the Momento API key.
         * @param controlHost An optional override for the endpoint used for control operations.
         * @param cacheHost An optional override for the endpoint used for cache operations.
         */
        public fun fromEnvVar(
            envVar: String, controlHost: String? = null, cacheHost: String? = null
        ): CredentialProvider {
            val apiKey = System.getenv(envVar) ?: throw IllegalArgumentException("Environment variable $envVar not set")
            return fromString(apiKey, controlHost, cacheHost)
        }

        private val json: Json = Json { ignoreUnknownKeys = true }
        private fun processLegacyKey(apiKey: String): CredentialProvider {
            val keyParts = apiKey.split(".")
            if (keyParts.size != 3) {
                throw IllegalArgumentException("Malformed legacy API key")
            }

            val payloadJson = keyParts[1].decodeBase64() ?: throw IllegalArgumentException("Cannot decode base64")
            val payload = json.decodeFromString<LegacyKeyPayload>(payloadJson)

            return CredentialProvider(payload.controlEndpoint, payload.cacheEndpoint, apiKey)
        }

        private fun processV1Key(apiKey: String): CredentialProvider {
            val decodedString = apiKey.decodeBase64() ?: throw IllegalArgumentException("Cannot decode base64")
            val v1Key = json.decodeFromString<V1Key>(decodedString)

            return CredentialProvider("control.${v1Key.host}", "cache.${v1Key.host}", v1Key.apiKey)
        }

        @Serializable
        private data class V1Key(
            @SerialName("endpoint") val host: String, @SerialName("api_key") val apiKey: String
        )

        @Serializable
        private data class LegacyKeyPayload(
            @SerialName("cp") val controlEndpoint: String, @SerialName("c") val cacheEndpoint: String
        )
    }
}
