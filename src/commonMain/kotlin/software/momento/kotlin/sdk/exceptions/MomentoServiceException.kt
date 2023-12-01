package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Base type for all the exceptions resulting from invalid interactions with Momento Services.
 */
public open class MomentoServiceException : SdkException {

    /**
     * Constructs a MomentoServiceException with an error code and a detail message.
     *
     * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     */
    public constructor(errorCode: MomentoErrorCode, message: String)
            : super(errorCode, message)

    /**
     * Constructs a MomentoServiceException with an error code, a detail message, and error details.
     *
     * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     * @param transportErrorDetails Details about the request and error.
     */
    public constructor(
        errorCode: MomentoErrorCode,
        message: String,
        transportErrorDetails: MomentoTransportErrorDetails
    ) : super(errorCode, message, null, transportErrorDetails)

    /**
     * Constructs a MomentoServiceException with an error code, a detail message, a cause, and error details.
     *
     * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     * @param cause The cause.
     * @param transportErrorDetails Details about the request and error.
     */
    public constructor(
        errorCode: MomentoErrorCode,
        message: String,
        cause: Throwable,
        transportErrorDetails: MomentoTransportErrorDetails? = null
    ) : super(errorCode, message, cause, transportErrorDetails)
}
