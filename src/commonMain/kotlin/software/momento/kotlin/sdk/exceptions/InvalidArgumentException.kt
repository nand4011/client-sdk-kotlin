package software.momento.kotlin.sdk.exceptions

/**
 * SDK client side validation fails.
 */
public class InvalidArgumentException : ClientSdkException {

    public constructor(message: String, cause: Throwable)
            : super(MomentoErrorCode.INVALID_ARGUMENT_ERROR, "$MESSAGE_PREFIX$message", cause)

    public constructor(message: String)
            : super(MomentoErrorCode.INVALID_ARGUMENT_ERROR, "$MESSAGE_PREFIX$message")

    private companion object {
        const val MESSAGE_PREFIX = "Invalid argument passed to Momento client: "
    }
}
