package com.tsci.domain.plants.repository

import com.tsci.domain.NetworkResult
import com.tsci.domain.plants.model.CategoryDomainModel
import com.tsci.domain.plants.model.QuestionDomainModel

interface PlantsRepository {

    suspend fun getQuestions(): NetworkResult<List<QuestionDomainModel>>

    suspend fun getCategories(): NetworkResult<List<CategoryDomainModel>>
}