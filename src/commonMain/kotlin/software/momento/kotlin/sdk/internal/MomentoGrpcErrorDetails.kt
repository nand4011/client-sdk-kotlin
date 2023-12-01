package software.momento.kotlin.sdk.internal

// todo: the code here will be a gRPC error code. To be added once gRPC deps are added
public data class MomentoGrpcErrorDetails(val code: Int, val details: String, val metadata: Map<String, Any>?)
