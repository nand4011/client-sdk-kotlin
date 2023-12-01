package software.momento.kotlin.sdk.exceptions

/**
 * Represents all client side exceptions thrown by the SDK.
 *
 * This exception typically implies that the request wasn't sent to the service successfully, or
 * if the service responded, the SDK couldn't interpret the response. An example would be if the
 * SDK client was unable to convert the user-provided data into a valid request expected by the
 * service.
 */
public open class ClientSdkException : SdkException {

    /**
     * Constructs a ClientSdkException with an error code, a detail message, and a cause.
     *
     * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     * @param cause The cause.
     */
    public constructor(errorCode: MomentoErrorCode, message: String, cause: Throwable)
            : super(errorCode, message, cause)

    /**
     * Constructs a ClientSdkException with an error code and a detail message.
     *
     * @param errorCode The error code, or [MomentoErrorCode.UNKNOWN] if none exists.
     * @param message The detail message.
     */
    public constructor(errorCode: MomentoErrorCode, message: String)
            : super(errorCode, message)

    /**
     * Constructs a ClientSdkException with a detail message.
     *
     * @param message The detail message.
     */
    public constructor(message: String)
            : super(MomentoErrorCode.UNKNOWN, message)
}
