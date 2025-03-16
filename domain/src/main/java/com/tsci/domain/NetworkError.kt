package com.tsci.domain


data class NetworkError(
    val status: Int? = null,
    val type: NetworkErrorType? = null,
    val path: String? = null,
)

enum class NetworkErrorType {
    SERIALIZATION,
    HTTP,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER,
    UNKNOWN,
}