package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Invalid parameters sent to Momento Services.
 */
public class BadRequestException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.BAD_REQUEST_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails) {
    private companion object {
        const val MESSAGE = "The request was invalid; please contact Momento"
    }
}
