package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Service returned an unknown response.
 */
public class UnknownServiceException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.UNKNOWN_SERVICE_ERROR,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "The service returned an unknown response; please contact Momento."
    }
}
