package software.momento.kotlin.sdk.internal

import com.google.protobuf.ByteString
import grpc.cache_client.ECacheResult
import grpc.cache_client._DeleteRequest
import grpc.cache_client._GetRequest
import grpc.cache_client._SetRequest
import software.momento.kotlin.sdk.internal.utils.ValidationUtils
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configuration
import software.momento.kotlin.sdk.exceptions.CacheServiceExceptionMapper
import software.momento.kotlin.sdk.exceptions.InternalServerException
import software.momento.kotlin.sdk.internal.utils.ByteStringExtensions.toByteString
import software.momento.kotlin.sdk.responses.cache.DeleteResponse
import software.momento.kotlin.sdk.responses.cache.GetResponse
import software.momento.kotlin.sdk.responses.cache.SetResponse
import kotlin.time.Duration

internal actual class InternalDataClient actual constructor(
    credentialProvider: CredentialProvider, configuration: Configuration, private val itemDefaultTtl: Duration
) : InternalClient() {
    private val stubsManager: DataGrpcStubsManager

    init {
        stubsManager = DataGrpcStubsManager(credentialProvider, configuration.transportStrategy.grpcConfiguration)
    }

    internal actual suspend fun set(
        cacheName: String, key: String, value: String, ttl: Duration?
    ): SetResponse = validateAndSendSet(cacheName, key.toByteString(), value.toByteString(), ttl)

    internal actual suspend fun set(
        cacheName: String, key: ByteArray, value: String, ttl: Duration?
    ): SetResponse = validateAndSendSet(cacheName, key.toByteString(), value.toByteString(), ttl)

    internal actual suspend fun set(
        cacheName: String, key: String, value: ByteArray, ttl: Duration?
    ): SetResponse = validateAndSendSet(cacheName, key.toByteString(), value.toByteString(), ttl)

    internal actual suspend fun set(
        cacheName: String, key: ByteArray, value: ByteArray, ttl: Duration?
    ): SetResponse = validateAndSendSet(cacheName, key.toByteString(), value.toByteString(), ttl)

    private suspend fun validateAndSendSet(
        cacheName: String, key: ByteString, value: ByteString, ttl: Duration?
    ): SetResponse {
        val effectiveTtl = ttl ?: itemDefaultTtl
        return runCatching {
            ValidationUtils.requireValidCacheName(cacheName)
            ValidationUtils.requireValidTtl(effectiveTtl)
        }.fold(onSuccess = {
            sendSet(cacheName, key, value, effectiveTtl)
        }, onFailure = { e ->
            SetResponse.Error(CacheServiceExceptionMapper.convert(e))
        })
    }

    private suspend fun sendSet(
        cacheName: String, key: ByteString, value: ByteString, ttl: Duration
    ): SetResponse {

        val request = _SetRequest.newBuilder().apply {
            cacheKey = key
            cacheBody = value
            ttlMilliseconds = ttl.inWholeMilliseconds
        }.build()

        val metadata = metadataWithCache(cacheName)

        return runCatching {
            stubsManager.stub.set(request, metadata)
        }.fold(onSuccess = {
            SetResponse.Success
        }, onFailure = { e ->
            SetResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        })
    }

    internal actual suspend fun get(
        cacheName: String, key: String
    ): GetResponse = validateAndSendGet(cacheName, key.toByteString())

    internal actual suspend fun get(
        cacheName: String, key: ByteArray
    ): GetResponse = validateAndSendGet(cacheName, key.toByteString())

    private suspend fun validateAndSendGet(
        cacheName: String, key: ByteString
    ): GetResponse {
        return runCatching {
            ValidationUtils.requireValidCacheName(cacheName)
        }.fold(onSuccess = {
            sendGet(cacheName, key)
        }, onFailure = { e ->
            GetResponse.Error(CacheServiceExceptionMapper.convert(e))
        })
    }

    private suspend fun sendGet(
        cacheName: String, key: ByteString
    ): GetResponse {
        val metadata = metadataWithCache(cacheName)

        val request = _GetRequest.newBuilder().apply {
            cacheKey = key
        }.build()

        return runCatching {
            stubsManager.stub.get(request, metadata)
        }.fold(onSuccess = { getResponse ->
            when (getResponse.result) {
                ECacheResult.Hit -> GetResponse.Hit(getResponse.cacheBody.toByteArray())
                ECacheResult.Miss -> GetResponse.Miss
                else -> GetResponse.Error(InternalServerException("Unsupported cache Get result: ${getResponse.result}"))
            }
        }, onFailure = { e ->
            GetResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        })
    }

    internal actual suspend fun delete(
        cacheName: String, key: String
    ): DeleteResponse = validateAndSendDelete(cacheName, key.toByteString())

    internal actual suspend fun delete(
        cacheName: String, key: ByteArray
    ): DeleteResponse = validateAndSendDelete(cacheName, key.toByteString())

    private suspend fun validateAndSendDelete(
        cacheName: String, key: ByteString
    ): DeleteResponse {
        return runCatching {
            ValidationUtils.requireValidCacheName(cacheName)
        }.fold(onSuccess = {
            sendDelete(cacheName, key)
        }, onFailure = { e ->
            DeleteResponse.Error(CacheServiceExceptionMapper.convert(e))
        })
    }

    private suspend fun sendDelete(
        cacheName: String, key: ByteString
    ): DeleteResponse {
        val metadata = metadataWithCache(cacheName)

        val request = _DeleteRequest.newBuilder().apply {
            cacheKey = key
        }.build()

        return runCatching {
            stubsManager.stub.delete(request, metadata)
        }.fold(onSuccess = {
            DeleteResponse.Success
        }, onFailure = { e ->
            DeleteResponse.Error(CacheServiceExceptionMapper.convert(e, metadata))
        })
    }

    override fun close() {
        stubsManager.close()
    }
}
