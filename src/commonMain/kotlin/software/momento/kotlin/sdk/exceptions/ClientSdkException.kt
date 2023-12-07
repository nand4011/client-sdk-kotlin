package software.momento.kotlin.sdk.exceptions

/**
 * Represents all client side exceptions thrown by the SDK.
 *
 * This exception typically implies that the request wasn't sent to the service successfully, or
 * if the service responded, the SDK couldn't interpret the response. An example would be if the
 * SDK client was unable to convert the user-provided data into a valid request expected by the
 * service.
 *
 * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
 * @param message The detail message.
 * @param cause The cause.
 */
public open class ClientSdkException(
    errorCode: MomentoErrorCode = MomentoErrorCode.UNKNOWN,
    message: String,
    cause: Throwable? = null
) : SdkException(errorCode, message, cause)
