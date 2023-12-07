package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * The server temporarily could not be reached.
 */
public class ServerUnavailableException(
    cause: Throwable,
    transportErrorDetails: MomentoTransportErrorDetails
) : MomentoServiceException(
    MomentoErrorCode.SERVER_UNAVAILABLE,
    MESSAGE,
    cause,
    transportErrorDetails
) {
    private companion object {
        const val MESSAGE = "The server was unable to handle the request; consider retrying. If the error persists, please contact Momento."
    }
}
