package com.tsci.domain


/**
 * A sealed class that represents the result of a network operation, encapsulating either a success or an error state.
 *
 * @param T The type of data expected in a successful network response.
 *
 */
sealed class NetworkResult<out T> {

    /**
     * Represents a successful network operation, containing the resulting data.
     *
     * @param T The type of data returned from the network operation.
     * @property data The data returned from the network, which may be `null`.
     */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /**
     * Represents a failed network operation, containing the error details.
     *
     * @property error An instance of `NetworkError` describing the error encountered.
     */
    data class Error(val error: NetworkError) : NetworkResult<Nothing>()
}