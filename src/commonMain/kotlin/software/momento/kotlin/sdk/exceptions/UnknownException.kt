package software.momento.kotlin.sdk.exceptions

/**
 * Unrecognized error.
 */
public class UnknownException : MomentoServiceException {

    public constructor(message: String)
            : super(MomentoErrorCode.UNKNOWN, message)

    public constructor(message: String, cause: Throwable)
            : super(MomentoErrorCode.UNKNOWN, message, cause, null)
}
