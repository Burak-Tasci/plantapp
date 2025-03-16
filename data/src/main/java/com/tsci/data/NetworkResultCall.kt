package com.tsci.data

import com.tsci.domain.NetworkError
import com.tsci.domain.NetworkErrorType
import com.tsci.domain.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>,
    private val type: Type,
) : Call<NetworkResult<T>> {

    /**
     * Overrides the `enqueue` method to handle network requests and wrap the results in
     * a `NetworkResult`. This ensures that the callback receives either a successful response or
     * an error wrapped in a `NetworkResult`.
     *
     * @param callback The `Callback` to be invoked with the `NetworkResult` containing either
     *      the data or an error.
     */
    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = parseResponse(response)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult: NetworkResult<T> = NetworkResult.Error(
                    NetworkError(
                        status = -1,
                        type = NetworkErrorType.UNKNOWN,
                        path = call.request().url.toString()
                    )
                )
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone(), type)
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() {
        proxy.cancel()
    }

    /**
     * Parses a network response and returns a `NetworkResult` encapsulating either a success or an error state.
     *
     * @param T The type of data expected in a successful response.
     * @param response The `Response` object returned from the network request.
     * @return A `NetworkResult` containing the response data if successful, or an error if the response failed.
     */
    internal fun <T> parseResponse(response: Response<T>): NetworkResult<T> {


        return if (response.isSuccessful) {
            try {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            } catch (exception: Exception) {
                NetworkResult.Error(
                    NetworkError(
                        status = -1,
                        type = NetworkErrorType.SERIALIZATION,
                        path = response.raw().request.url.toString()
                    )
                )
            }
        } else {

            val statusCode = response.code()
            val path = response.raw().request.url.toString()

            val error = NetworkError(
                status = statusCode,
                path = path,
                type = when (statusCode) {
                    408 -> NetworkErrorType.REQUEST_TIMEOUT
                    429 -> NetworkErrorType.TOO_MANY_REQUESTS
                    in 400..499 -> {
                        NetworkErrorType.HTTP
                    }

                    in 500..599 -> {
                        NetworkErrorType.SERVER
                    }

                    else -> {
                        NetworkErrorType.UNKNOWN
                    }
                }
            )
            return NetworkResult.Error(error = error)


        }
    }
}

