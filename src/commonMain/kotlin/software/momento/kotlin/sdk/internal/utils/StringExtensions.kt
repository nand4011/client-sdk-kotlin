package software.momento.kotlin.sdk.internal.utils

internal fun String.truncate(length: Int): String {
    return if (length <= 0) {
        ""
    } else if (length >= this.length) {
        this
    } else {
        this.substring(0, length) + "..."
    }
}
