package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Requested resource or the resource on which an operation was requested doesn't exist.
 */
public class NotFoundException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.NOT_FOUND_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "A cache with the specified name does not exist. To resolve this error, make sure you have created the cache before attempting to use it."
    }
}
