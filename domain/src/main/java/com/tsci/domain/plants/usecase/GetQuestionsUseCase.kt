package com.tsci.domain.plants.usecase

import com.tsci.domain.NetworkResult
import com.tsci.domain.plants.model.QuestionDomainModel
import com.tsci.domain.plants.repository.PlantsRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: PlantsRepository
): IGetQuestionsUseCase {

    override suspend fun get(): NetworkResult<List<QuestionDomainModel>> {
        return repository.getQuestions()
    }
}

interface IGetQuestionsUseCase {
    suspend fun get(): NetworkResult<List<QuestionDomainModel>>
}