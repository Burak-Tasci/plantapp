package com.tsci.data

import com.tsci.domain.NetworkResult

inline fun <reified T, reified R> execute(
    apiCall: () -> NetworkResult<T>,
    mapper: (T) -> R
): NetworkResult<R> {
    return when (val result = apiCall()) {
        is NetworkResult.Error -> NetworkResult.Error(result.error)
        is NetworkResult.Success -> NetworkResult.Success(mapper.invoke(result.data))
    }
}