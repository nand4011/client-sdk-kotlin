package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Insufficient permissions to execute an operation.
 */
public class PermissionDeniedException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.PERMISSION_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "Insufficient permissions to perform an operation on a cache."
    }
}
