package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Base class for all exceptions thrown by the SDK.
 *
 * @property errorCode The Momento error code associated with this exception.
 * @property transportErrorDetails Optional details about the transport layer error, if any.
 */
public open class SdkException : RuntimeException {
    public val errorCode: MomentoErrorCode
    public val transportErrorDetails: MomentoTransportErrorDetails?

    /**
     * Constructs an SdkException from another SdkException.
     * The detail message, error code, and error details are copied from the given exception,
     * and the cause is set to the given exception.
     *
     * @param sdkException The exception to wrap.
     */
    public constructor(sdkException: SdkException) : super(sdkException.message, sdkException) {
        errorCode = sdkException.errorCode
        transportErrorDetails = sdkException.transportErrorDetails
    }

    /**
     * Constructs an SdkException with an error code, a detail message,
     * an optional cause, and optional transport error details.
     *
     * @param errorCode The Momento error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     * @param cause The cause of the exception, or null if the cause is nonexistent or unknown.
     * @param transportErrorDetails Details about the transport error, or null if not applicable.
     */
    public constructor(
        errorCode: MomentoErrorCode,
        message: String,
        cause: Throwable? = null,
        transportErrorDetails: MomentoTransportErrorDetails? = null
    ) : super(message, cause) {
        this.errorCode = errorCode
        this.transportErrorDetails = transportErrorDetails
    }

    /**
     * Constructs an SdkException with an error code, a detail message, and a cause.
     *
     * @param errorCode The Momento error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public constructor(errorCode: MomentoErrorCode, message: String, cause: Throwable?)
            : this(errorCode, message, cause, null)

    /**
     * Constructs an SdkException with an error code and a detail message.
     *
     * @param errorCode The Momento error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     */
    public constructor(errorCode: MomentoErrorCode, message: String)
            : this(errorCode, message, null, null)

    /**
     * Constructs an SdkException with a detail message and an [MomentoErrorCode.UNKNOWN] error code.
     *
     * @param message The detail message.
     */
    public constructor(message: String) : this(MomentoErrorCode.UNKNOWN, message, null, null)
}
