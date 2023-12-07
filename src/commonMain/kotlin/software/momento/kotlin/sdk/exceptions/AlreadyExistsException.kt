package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * A resource already exists.
 */
public class AlreadyExistsException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.ALREADY_EXISTS_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails) {
    private companion object {
        const val MESSAGE = "A cache with the specified name already exists. To resolve this error, either delete the existing cache and make a new one, or use a different name."
    }
}
