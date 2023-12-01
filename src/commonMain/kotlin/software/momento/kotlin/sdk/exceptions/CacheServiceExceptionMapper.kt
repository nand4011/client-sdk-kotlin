package software.momento.kotlin.sdk.exceptions

/**
 * Mapper from any exception that may occur during a cache client call to the appropriate [SdkException].
 */
public object CacheServiceExceptionMapper {

    private const val SDK_FAILED_TO_PROCESS_THE_REQUEST = "SDK Failed to process the request."

    /**
     * Common Handler for converting exceptions encountered by the SDK. Any specialized exception
     * handling should be performed before calling this.
     *
     * @param e The exception to convert.
     * @return The converted exception.
     */
    public fun convert(e: Throwable): SdkException {
        return convert(e, null)
    }

    /**
     * Common Handler for converting exceptions encountered by the SDK. Any specialized exception
     * handling should be performed before calling this.
     *
     * @param e The exception to convert.
     * @param metadata Metadata from the grpc request that caused the error.
     * @return The converted exception.
     */
    // todo: metadata below isn't gRPC one is kotlin stdlib one .. needs to be changed once gRPC deps are added
    public fun convert(e: Throwable, metadata: Metadata?): SdkException {
        if (e is SdkException) {
            return e
        }

        // todo: add gRPC conversion

        return UnknownException(SDK_FAILED_TO_PROCESS_THE_REQUEST, e)
    }
}
