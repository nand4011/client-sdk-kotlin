package software.momento.kotlin.sdk.exceptions

import software.momento.kotlin.sdk.internal.MomentoTransportErrorDetails

/**
 * Momento Service encountered an unexpected exception while trying to fulfill the request.
 */
public class InternalServerException : MomentoServiceException {

    public constructor(message: String)
            : super(MomentoErrorCode.INTERNAL_SERVER_ERROR, "$BASE_MESSAGE $message")

    public constructor(
        cause: Throwable,
        transportErrorDetails: MomentoTransportErrorDetails
    ) : super(
        MomentoErrorCode.INTERNAL_SERVER_ERROR,
        BASE_MESSAGE,
        cause,
        transportErrorDetails
    )

    private companion object {
        const val BASE_MESSAGE = "An unexpected error occurred while trying to fulfill the request; please contact Momento."
    }
}
