package com.tsci.data.sample.repository

import com.tsci.data.sample.model.SampleResponse
import com.tsci.data.sample.model.toDomainModel
import com.tsci.data.sample.service.SampleApiService
import com.tsci.domain.NetworkResult
import com.tsci.domain.sample.model.SampleDomainModel
import com.tsci.domain.sample.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val apiService: SampleApiService
) : SampleRepository {

    override suspend fun getSampleData(): NetworkResult<SampleDomainModel> {
        return execute(
            apiCall = { apiService.getSampleData() },
            mapper = SampleResponse::toDomainModel
        )
    }
}


inline fun <reified T, reified R> execute(
    apiCall: () -> NetworkResult<T>,
    mapper: (T) -> R
): NetworkResult<R> {
    return when (val result = apiCall()) {
        is NetworkResult.Error -> NetworkResult.Error(result.error)
        is NetworkResult.Success -> NetworkResult.Success(mapper.invoke(result.data))
    }
}