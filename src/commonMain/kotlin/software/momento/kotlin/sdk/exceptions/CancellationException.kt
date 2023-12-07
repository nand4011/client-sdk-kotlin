package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Operation was cancelled.
 */
public class CancellationException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.CANCELLED_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "The request was cancelled by the server; please contact Momento."
    }
}
