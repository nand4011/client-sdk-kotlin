package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Base type for all the exceptions resulting from invalid interactions with Momento Services.
 *
 * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
 * @param message The detail message.
 * @param cause The cause.
 * @param transportErrorDetails Details about the request and error.
 */
public open class MomentoServiceException(
    errorCode: MomentoErrorCode,
    message: String,
    cause: Throwable? = null,
    transportErrorDetails: MomentoTransportErrorDetails? = null
) : SdkException(errorCode, message, cause, transportErrorDetails)
