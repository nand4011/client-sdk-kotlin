package software.momento.kotlin.sdk.auth

import software.momento.kotlin.sdk.UsingTestRunner
import software.momento.kotlin.sdk.exceptions.InvalidArgumentException
import java.util.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CredentialProviderTest : UsingTestRunner() {

    companion object {
        private const val CONTROL_ENDPOINT_LEGACY = "control.example.com"
        private const val CACHE_ENDPOINT_LEGACY = "cache.example.com"
        private const val CONTROL_ENDPOINT_V1 = "control.test.momentohq.com"
        private const val CACHE_ENDPOINT_V1 = "cache.test.momentohq.com"

        // Test tokens are all fake and nonfunctional.

        private const val LEGACY_API_KEY_VALID =
            ("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzcXVpcnJlbCIsImNwIjoiY29udHJvbC5leGFtcGxlLmNvbSIsImMiOiJjYWNoZS5leGFtcG" +
                    "xlLmNvbSJ9.YY7RSMBCpMRs_qgbNkW0PYC2eX-MukLixLWJyvBpnMVaOba-OV0G5jgNmNbtn4zaLT8tlEncV6wQ_CkTI_PvoA")
        private const val V1_API_KEY_VALID =
            ("eyJhcGlfa2V5IjogImV5SjBlWEFpT2lKS1YxUWlMQ0poYkdjaU9pSklVekkxTmlKOS5leUpwYzNNaU9pSlBibXhwYm1VZ1NsZFVJRUo" +
                    "xYVd4a1pYSWlMQ0pwWVhRaU9qRTJOemd6TURVNE1USXNJbVY0Y0NJNk5EZzJOVFV4TlRReE1pd2lZWFZrSWpvaUlpd2ljM1Z" +
                    "pSWpvaWFuSnZZMnRsZEVCbGVHRnRjR3hsTG1OdmJTSjkuOEl5OHE4NExzci1EM1lDb19IUDRkLXhqSGRUOFVDSXV2QVljeGh" +
                    "GTXl6OCIsICJlbmRwb2ludCI6ICJ0ZXN0Lm1vbWVudG9ocS5jb20ifQ==")
        private const val V1_INNER_KEY =
            ("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2NzgzMDU4MTIsImV4cC" +
                    "I6NDg2NTUxNTQxMiwiYXVkIjoiIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSJ9.8Iy8q84Lsr-D3YCo_HP4d-xjHdT8U" +
                    "CIuvAYcxhFMyz8")
        private const val V1_API_KEY_MISSING_ENDPOINT =
            ("eyJhcGlfa2V5IjogImV5SmxibVJ3YjJsdWRDSTZJbU5sYkd3dE5DMTFjeTEzWlhOMExUSXRNUzV3Y205a0xtRXViVzl0Wlc1MGIyaHh" +
                    "MbU52YlNJc0ltRndhVjlyWlhraU9pSmxlVXBvWWtkamFVOXBTa2xWZWtreFRtbEtPUzVsZVVwNlpGZEphVTlwU25kYVdGSnN" +
                    "URzFrYUdSWVVuQmFXRXBCV2pJeGFHRlhkM1ZaTWpsMFNXbDNhV1J0Vm5sSmFtOTRabEV1VW5OMk9GazVkRE5KVEMwd1RHRjZ" +
                    "iQzE0ZDNaSVZESmZZalJRZEhGTlVVMDVRV3hhVlVsVGFrbENieUo5In0=")
        private const val V1_API_KEY_MISSING_KEY = "eyJlbmRwb2ludCI6ICJhLmIuY29tIn0="
        const val LEGACY_API_KEY_MISSING_CONTROL_TOKEN =
            ("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzcXVpcnJlbCIsImMiOiJjYWNoZS5leGFtcGxlLmNvbSJ9.RzLpBXut4s0fEXHtVIYVNb6Z8" +
                    "tiHSP9iu2j6OJpJHDksNXuOgTVFlMyG4V3gvMLMUwQmgtov-U9pMbaghQnr-Q")
        const val LEGACY_API_KEY_MISSING_CACHE_TOKEN =
            ("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzcXVpcnJlbCIsImNwIjoiY29udHJvbC5leGFtcGxlLmNvbSJ9.obg5-runV-bdp0ZTV_2DG" +
                    "DFdRfc6aIRHaSBGbK3QaACPXwF6e8ghBYg2LDXXOWgbdpy6wEfDVIPgYZ0yXxVqvg")
    }

    @Test
    fun testCredentialProviderMissingEnvVar() {
        val envVar = UUID.randomUUID().toString()
        try {
            CredentialProvider.fromEnvVar(envVar)
        } catch (e: InvalidArgumentException) {
            assertContains(e.message!!, "not set")
        }
    }

    @Test(expected = InvalidArgumentException::class)
    fun testCredentialProviderUnparsableToken() {
        CredentialProvider.fromString("this isn't a real Token")
    }

    @Test(expected = InvalidArgumentException::class)
    fun testCredentialProviderNoControlEndpoint() {
        CredentialProvider.fromString(LEGACY_API_KEY_MISSING_CONTROL_TOKEN)
    }

    @Test(expected = InvalidArgumentException::class)
    fun testCredentialProviderNoCacheEndpoint() {
        CredentialProvider.fromString(LEGACY_API_KEY_MISSING_CACHE_TOKEN)
    }

    @Test(expected = InvalidArgumentException::class)
    fun testCredentialProviderV1NoApiKey() {
        CredentialProvider.fromString(V1_API_KEY_MISSING_KEY)
    }

    @Test(expected = InvalidArgumentException::class)
    fun testCredentialProviderV1NoEndpoint() {
        CredentialProvider.fromString(V1_API_KEY_MISSING_ENDPOINT)
    }

    @Test
    fun testCredentialProviderLegacyTokenHappyPath() {
        val provider = CredentialProvider.fromString(LEGACY_API_KEY_VALID)
        assertEquals(LEGACY_API_KEY_VALID, provider.apiKey)
        assertEquals(CONTROL_ENDPOINT_LEGACY, provider.controlEndpoint)
        assertEquals(CACHE_ENDPOINT_LEGACY, provider.cacheEndpoint)
    }

    @Test
    fun testCredentialProviderLegacyTokenOverrideEndpointsHappyPath() {
        val controlOverride = "my.control.host"
        val cacheOverride = "my.cache.host"

        val controlOverrideProvider =
            CredentialProvider.fromString(LEGACY_API_KEY_VALID, controlHost = controlOverride)
        assertEquals(LEGACY_API_KEY_VALID, controlOverrideProvider.apiKey)
        assertEquals(controlOverride, controlOverrideProvider.controlEndpoint)
        assertEquals(CACHE_ENDPOINT_LEGACY, controlOverrideProvider.cacheEndpoint)

        val cacheOverrideProvider = CredentialProvider.fromString(LEGACY_API_KEY_VALID, cacheHost = cacheOverride)
        assertEquals(LEGACY_API_KEY_VALID, cacheOverrideProvider.apiKey)
        assertEquals(CONTROL_ENDPOINT_LEGACY, cacheOverrideProvider.controlEndpoint)
        assertEquals(cacheOverride, cacheOverrideProvider.cacheEndpoint)

        val bothOverrideProvider = CredentialProvider.fromString(
            LEGACY_API_KEY_VALID, controlHost = controlOverride, cacheHost = cacheOverride
        )
        assertEquals(LEGACY_API_KEY_VALID, bothOverrideProvider.apiKey)
        assertEquals(controlOverride, bothOverrideProvider.controlEndpoint)
        assertEquals(cacheOverride, bothOverrideProvider.cacheEndpoint)
    }

    @Test
    fun testCredentialProviderV1TokenHappyPath() {
        val provider = CredentialProvider.fromString(V1_API_KEY_VALID)
        assertEquals(V1_INNER_KEY, provider.apiKey)
        assertEquals(CONTROL_ENDPOINT_V1, provider.controlEndpoint)
        assertEquals(CACHE_ENDPOINT_V1, provider.cacheEndpoint)
    }

    @Test
    fun testCredentialProviderV1TokenOverrideEndpointsHappyPath() {
        val controlOverride = "my.control.host"
        val cacheOverride = "my.cache.host"

        val controlOverrideProvider = CredentialProvider.fromString(V1_API_KEY_VALID, controlHost = controlOverride)
        assertEquals(V1_INNER_KEY, controlOverrideProvider.apiKey)
        assertEquals(controlOverride, controlOverrideProvider.controlEndpoint)
        assertEquals(CACHE_ENDPOINT_V1, controlOverrideProvider.cacheEndpoint)

        val cacheOverrideProvider = CredentialProvider.fromString(V1_API_KEY_VALID, cacheHost = cacheOverride)
        assertEquals(V1_INNER_KEY, cacheOverrideProvider.apiKey)
        assertEquals(CONTROL_ENDPOINT_V1, cacheOverrideProvider.controlEndpoint)
        assertEquals(cacheOverride, cacheOverrideProvider.cacheEndpoint)

        val bothOverrideProvider =
            CredentialProvider.fromString(V1_API_KEY_VALID, controlHost = controlOverride, cacheHost = cacheOverride)
        assertEquals(V1_INNER_KEY, bothOverrideProvider.apiKey)
        assertEquals(controlOverride, bothOverrideProvider.controlEndpoint)
        assertEquals(cacheOverride, bothOverrideProvider.cacheEndpoint)
    }
}

