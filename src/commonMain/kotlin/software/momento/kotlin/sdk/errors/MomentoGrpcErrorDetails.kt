package software.momento.kotlin.sdk.errors

public data class MomentoGrpcErrorDetails(val code: Int, val details: String, val metadata: Map<String, Any>?)
