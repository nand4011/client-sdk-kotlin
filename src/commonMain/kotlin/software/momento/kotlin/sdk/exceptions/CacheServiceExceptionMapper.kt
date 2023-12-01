package momento.sdk.exceptions

import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException
import software.momento.kotlin.sdk.exceptions.AlreadyExistsException
import software.momento.kotlin.sdk.exceptions.AuthenticationException
import software.momento.kotlin.sdk.exceptions.BadRequestException
import software.momento.kotlin.sdk.exceptions.CancellationException
import software.momento.kotlin.sdk.exceptions.InternalServerException
import software.momento.kotlin.sdk.exceptions.LimitExceededException
import software.momento.kotlin.sdk.exceptions.NotFoundException
import software.momento.kotlin.sdk.exceptions.PermissionDeniedException
import software.momento.kotlin.sdk.exceptions.SdkException
import software.momento.kotlin.sdk.exceptions.ServerUnavailableException
import software.momento.kotlin.sdk.exceptions.TimeoutException
import software.momento.kotlin.sdk.exceptions.UnknownException
import software.momento.kotlin.sdk.exceptions.UnknownServiceException
import software.momento.kotlin.sdk.internal.MomentoGrpcErrorDetails
import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Mapper from any exception that may occur during a cache client call to the appropriate [ ].
 */
public object CacheServiceExceptionMapper {
    private const val SDK_FAILED_TO_PROCESS_THE_REQUEST = "SDK Failed to process the request."
    /**
     * Common Handler for converting exceptions encountered by the SDK. Any specialized exception
     * handling should be performed before calling this.
     *
     * @param e The exception to convert.
     * @param metadata Metadata from the grpc request that caused the error.
     * @return The converted exception.
     */
    /**
     * Common Handler for converting exceptions encountered by the SDK. Any specialized exception
     * handling should be performed before calling this.
     *
     * @param e The exception to convert.
     * @return The converted exception.
     */
    @JvmOverloads
    public fun convert(e: Throwable?, metadata: Metadata? = null): SdkException {
        if (e is SdkException) {
            return e
        }
        if (e is StatusRuntimeException) {
            val grpcException = e
            val statusCode = grpcException.status.code
            val errorDetails = MomentoTransportErrorDetails(
                MomentoGrpcErrorDetails(statusCode, grpcException.message!!, metadata)
            )
            return when (statusCode) {
                Status.Code.INVALID_ARGUMENT, Status.Code.UNIMPLEMENTED, Status.Code.OUT_OF_RANGE, Status.Code.FAILED_PRECONDITION -> BadRequestException(
                    grpcException,
                    errorDetails
                )

                Status.Code.CANCELLED -> CancellationException(grpcException, errorDetails)
                Status.Code.DEADLINE_EXCEEDED -> TimeoutException(grpcException, errorDetails)
                Status.Code.PERMISSION_DENIED -> PermissionDeniedException(
                    grpcException,
                    errorDetails
                )

                Status.Code.UNAUTHENTICATED -> AuthenticationException(grpcException, errorDetails)
                Status.Code.RESOURCE_EXHAUSTED -> LimitExceededException(
                    grpcException,
                    errorDetails
                )

                Status.Code.NOT_FOUND -> NotFoundException(grpcException, errorDetails)
                Status.Code.ALREADY_EXISTS -> AlreadyExistsException(grpcException, errorDetails)
                Status.Code.UNKNOWN -> UnknownServiceException(grpcException, errorDetails)
                Status.Code.UNAVAILABLE -> ServerUnavailableException(grpcException, errorDetails)
                Status.Code.ABORTED, Status.Code.INTERNAL, Status.Code.DATA_LOSS -> InternalServerException(
                    e,
                    errorDetails
                )

                else -> InternalServerException(e, errorDetails)
            }
        }
        return UnknownException(
            SDK_FAILED_TO_PROCESS_THE_REQUEST,
            e!!
        )
    }
}