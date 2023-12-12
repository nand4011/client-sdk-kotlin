package software.momento.kotlin.sdk.internal.utils

import software.momento.kotlin.sdk.exceptions.InvalidArgumentException
import kotlin.time.Duration

/**
 * Client-side validation methods. While we should rely on server for all validations, there are
 * some that cannot be delegated and instead fail in grpc client, like providing a negative ttl.
 */
public object ValidationUtils {
    private const val REQUEST_DEADLINE_MUST_BE_POSITIVE = "Request deadline must be positive"
    private const val CACHE_ITEM_TTL_MUST_BE_POSITIVE = "Cache item TTL must be positive"
    private const val CACHE_NAME_IS_REQUIRED = "Non-empty cache name is required"

    /**
     * Throws an [InvalidArgumentException] if the deadline is null or not positive.
     *
     * @param deadline The deadline to validate.
     */
    internal fun requireValidDeadline(deadline: Duration) {
        require(deadline.isPositive()) { REQUEST_DEADLINE_MUST_BE_POSITIVE }
    }

    internal fun requireValidCacheName(cacheName: String) {
        require(cacheName.isNotBlank()) { CACHE_NAME_IS_REQUIRED }
    }

    internal fun requireValidTtl(ttl: Duration) {
        require(ttl.isPositive()) { CACHE_ITEM_TTL_MUST_BE_POSITIVE }
    }
}
