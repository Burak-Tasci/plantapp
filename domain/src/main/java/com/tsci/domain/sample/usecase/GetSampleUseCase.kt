package com.tsci.domain.sample.usecase

import com.tsci.domain.NetworkResult
import com.tsci.domain.sample.model.SampleDomainModel
import com.tsci.domain.sample.repository.SampleRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetSampleUseCase @Inject constructor(
    private val repository: SampleRepository
): IGetSampleUseCase {

    override suspend fun getName(): NetworkResult<SampleDomainModel>  {
        delay(3000L)
        val response = repository.getSampleData()
        return response
    }
}

interface IGetSampleUseCase {
    suspend fun getName(): NetworkResult<SampleDomainModel>
}