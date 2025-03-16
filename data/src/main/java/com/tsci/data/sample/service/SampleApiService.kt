package com.tsci.data.sample.service

import com.tsci.data.sample.model.SampleResponse
import com.tsci.domain.NetworkResult

interface SampleApiService {

    suspend fun getSampleData(): NetworkResult<SampleResponse>

}