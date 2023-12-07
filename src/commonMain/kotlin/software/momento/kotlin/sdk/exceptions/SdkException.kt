package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Base class for all exceptions thrown by the SDK.
 *
 * @property errorCode The Momento error code associated with this exception.
 * @property transportErrorDetails Optional details about the transport layer error, if any.
 * @param message The detail message.
 * @param cause The cause of the exception.
 */
public open class SdkException(
    public val errorCode: MomentoErrorCode = MomentoErrorCode.UNKNOWN,
    message: String,
    cause: Throwable? = null,
    public val transportErrorDetails: MomentoTransportErrorDetails? = null
) : RuntimeException(message, cause) {

    /**
     * Constructs an SdkException from another SdkException.
     * The detail message, error code, and error details are copied from the given exception,
     * and the cause is set to the given exception.
     *
     * @param sdkException The exception to wrap.
     */
    public constructor(sdkException: SdkException) : this(
        sdkException.errorCode,
        sdkException.message ?: "", // Safe call on message since it can be null in Throwable
        sdkException,
        sdkException.transportErrorDetails
    )
}
