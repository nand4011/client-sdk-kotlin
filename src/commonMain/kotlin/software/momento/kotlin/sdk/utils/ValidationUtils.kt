package software.momento.kotlin.sdk.utils

import software.momento.kotlin.sdk.exceptions.InvalidArgumentException
import kotlin.time.Duration

/**
 * Client-side validation methods. While we should rely on server for all validations, there are
 * some that cannot be delegated and instead fail in grpc client, like providing null inputs or a
 * negative ttl.
 */
public object ValidationUtils {
    internal const val REQUEST_DEADLINE_MUST_BE_POSITIVE = "Request deadline must be positive"
    internal const val CACHE_ITEM_TTL_CANNOT_BE_NEGATIVE = "Cache item TTL cannot be negative."
    internal const val A_NON_NULL_KEY_IS_REQUIRED = "A non-null key is required."
    internal const val A_NON_NULL_VALUE_IS_REQUIRED = "A non-null value is required."
    internal const val CACHE_NAME_IS_REQUIRED = "Cache name is required."

    /**
     * Throws an [InvalidArgumentException] if the deadline is null or not positive.
     *
     * @param requestDeadline The deadline to validate.
     */
    public fun ensureRequestDeadlineValid(requestDeadline: Duration?) {
        if (requestDeadline == null || requestDeadline.isNegative() || requestDeadline.inWholeSeconds == 0L) {
            throw InvalidArgumentException(REQUEST_DEADLINE_MUST_BE_POSITIVE)
        }
    }

    internal fun checkCacheNameValid(cacheName: String?) {
        if (cacheName == null) {
            throw InvalidArgumentException(CACHE_NAME_IS_REQUIRED)
        }
    }

    internal fun ensureValidCacheSet(key: Any?, value: Any?, ttl: Duration) {
        ensureValidKey(key)
        ensureValidValue(value)
        ensureValidTtl(ttl)
    }

    internal fun ensureValidKey(key: Any?) {
        if (key == null) {
            throw InvalidArgumentException(A_NON_NULL_KEY_IS_REQUIRED)
        }
    }

    internal fun ensureValidValue(value: Any?) {
        if (value == null) {
            throw InvalidArgumentException(A_NON_NULL_VALUE_IS_REQUIRED)
        }
    }

    internal fun ensureValidTtl(ttl: Duration) {
        if (ttl.inWholeSeconds < 0) {
            throw InvalidArgumentException(CACHE_ITEM_TTL_CANNOT_BE_NEGATIVE)
        }
    }
}