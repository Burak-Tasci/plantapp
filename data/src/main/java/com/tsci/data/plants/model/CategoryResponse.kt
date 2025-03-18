package com.tsci.data.plants.model


import com.google.gson.annotations.SerializedName
import com.tsci.domain.plants.model.CategoryDomainModel

data class CategoryResponse(
    @SerializedName("data")
    val data: List<Data?>?,
    @SerializedName("meta")
    val meta: Meta?
)

fun CategoryResponse.toDomainModel(): List<CategoryDomainModel> {
    return data.orEmpty().filterNotNull().map {
        CategoryDomainModel(
            id = it.id ?: 0,
            title = it.title.orEmpty(),
            imageUrl = it.image?.url.orEmpty()
        )
    }
}