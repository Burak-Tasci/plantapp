package com.tsci.data.plants.repository

import com.tsci.data.execute
import com.tsci.data.plants.model.toDomainModel
import com.tsci.data.plants.service.PlantsApiService
import com.tsci.domain.NetworkResult
import com.tsci.domain.plants.model.CategoryDomainModel
import com.tsci.domain.plants.model.QuestionDomainModel
import com.tsci.domain.plants.repository.PlantsRepository
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(
    private val plantsApiService: PlantsApiService
): PlantsRepository {

    override suspend fun getQuestions(): NetworkResult<List<QuestionDomainModel>> {
        return execute(
            apiCall = { plantsApiService.getQuestions() },
            mapper = { it.toDomainModel() }
        )
    }

    override suspend fun getCategories(): NetworkResult<List<CategoryDomainModel>> {
        return execute(
            apiCall = { plantsApiService.getCategories() },
            mapper = { it.toDomainModel() }
        )
    }
}