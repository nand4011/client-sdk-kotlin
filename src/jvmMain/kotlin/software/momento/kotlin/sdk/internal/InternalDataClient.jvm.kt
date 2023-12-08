package software.momento.kotlin.sdk.internal

import com.google.protobuf.ByteString
import grpc.cache_client.ECacheResult
import grpc.cache_client._GetRequest
import grpc.cache_client._GetResponse
import grpc.cache_client._SetRequest
import io.grpc.Metadata
import software.momento.kotlin.sdk.utils.ValidationUtils
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.exceptions.CacheServiceExceptionMapper
import software.momento.kotlin.sdk.exceptions.InternalServerException
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import java.io.Closeable
import kotlin.time.Duration

internal actual class InternalDataClient actual constructor(
    credentialProvider: CredentialProvider,
    configuration: Configuration
): Closeable {
    private val stubsManager: DataGrpcStubsManager

    init {
        stubsManager = DataGrpcStubsManager(credentialProvider)
    }

    internal actual suspend fun set(
        cacheName: String, key: String, value: String, ttl: Duration
    ): SetResponse {

        ValidationUtils.checkCacheNameValid(cacheName)
        ValidationUtils.ensureValidCacheSet(key, value, ttl)

        val metadata = metadataWithCache(cacheName)

        val request = _SetRequest.newBuilder()
            .setCacheKey(convert(key.toByteArray()))
            .setCacheBody(convert(value.toByteArray()))
            .setTtlMilliseconds(ttl.inWholeMilliseconds)
            .build()

        try {
            stubsManager.stub.set(request, metadata)
        } catch (e: Exception) {
            return SetResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        }

        return SetResponse.Success
    }

    internal actual suspend fun get(
        cacheName: String, key: String): GetResponse {

        ValidationUtils.checkCacheNameValid(cacheName)

        val metadata = metadataWithCache(cacheName)

        val request = _GetRequest.newBuilder()
            .setCacheKey(convert(key.toByteArray()))
            .build()

        val getResponse : _GetResponse
        try {
            getResponse = stubsManager.stub.get(request, metadata)
        } catch (e: Exception) {
            return GetResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        }

        val result = getResponse.result
        if (result == ECacheResult.Hit) {
            return GetResponse.Hit(getResponse.cacheBody.toByteArray())
        } else if (result == ECacheResult.Miss) {
            return GetResponse.Miss
        }

        throw GetResponse.Error(InternalServerException("Unsupported cache Get result: $result"));
    }

    private fun convert(bytes: ByteArray?): ByteString? {
        return if (bytes == null) {
            ByteString.EMPTY
        } else ByteString.copyFrom(bytes)
    }


    companion object {
        private val CACHE_NAME_KEY = Metadata.Key.of("cache", Metadata.ASCII_STRING_MARSHALLER)

        fun metadataWithCache(cacheName: String): Metadata {
            val metadata = Metadata()
            metadata.put(CACHE_NAME_KEY, cacheName)
            return metadata
        }
    }

    override fun close() {
        stubsManager.close()
    }
}
