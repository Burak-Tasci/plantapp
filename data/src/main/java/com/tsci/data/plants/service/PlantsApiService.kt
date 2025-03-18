package com.tsci.data.plants.service

import com.tsci.data.plants.model.CategoryResponse
import com.tsci.data.plants.model.QuestionResponse
import com.tsci.domain.NetworkResult
import retrofit2.http.GET

interface PlantsApiService {

    @GET("getQuestions")
    suspend fun getQuestions(): NetworkResult<List<QuestionResponse>>

    @GET("getCategories")
    suspend fun getCategories(): NetworkResult<CategoryResponse>

}