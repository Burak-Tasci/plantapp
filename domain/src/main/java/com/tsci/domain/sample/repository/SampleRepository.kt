package com.tsci.domain.sample.repository

import com.tsci.domain.NetworkResult
import com.tsci.domain.sample.model.SampleDomainModel

interface SampleRepository {

    suspend fun getSampleData(): NetworkResult<SampleDomainModel>
}