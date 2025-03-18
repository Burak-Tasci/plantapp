package com.tsci.domain.plants.usecase

import com.tsci.domain.NetworkResult
import com.tsci.domain.plants.model.CategoryDomainModel
import com.tsci.domain.plants.repository.PlantsRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: PlantsRepository
): IGetCategoriesUseCase {

    override suspend fun get(): NetworkResult<List<CategoryDomainModel>> {
        return repository.getCategories()
    }

}

interface IGetCategoriesUseCase {
    suspend fun get(): NetworkResult<List<CategoryDomainModel>>
}