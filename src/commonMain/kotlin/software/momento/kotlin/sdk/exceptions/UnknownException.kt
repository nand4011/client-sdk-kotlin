package software.momento.kotlin.sdk.exceptions

/**
 * Unrecognized error.
 */
public class UnknownException(message: String, cause: Throwable? = null) :
    MomentoServiceException(MomentoErrorCode.UNKNOWN, message, cause)
