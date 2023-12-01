package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Requested operation couldn't be completed because system limits were hit.
 */
public class LimitExceededException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.LIMIT_EXCEEDED_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "Request rate, bandwidth, or object size exceeded the limits for this account. To resolve this error, reduce your usage as appropriate or contact Momento to request a limit increase."
    }
}
