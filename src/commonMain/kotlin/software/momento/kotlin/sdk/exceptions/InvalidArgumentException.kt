package software.momento.kotlin.sdk.exceptions

/**
 * SDK client side validation fails.
 */
public class InvalidArgumentException(message: String, cause: Throwable? = null) :
    ClientSdkException(MomentoErrorCode.INVALID_ARGUMENT_ERROR, "$MESSAGE_PREFIX$message", cause) {

    private companion object {
        const val MESSAGE_PREFIX = "Invalid argument passed to Momento client: "
    }
}
