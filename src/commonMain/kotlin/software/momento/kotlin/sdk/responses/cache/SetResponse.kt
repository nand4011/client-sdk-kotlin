package software.momento.kotlin.sdk.responses.cache

import software.momento.kotlin.sdk.exceptions.SdkException

public sealed interface SetResponse {

    public object Success : SetResponse

    public class Error(cause: SdkException): SdkException(cause), SetResponse
}
