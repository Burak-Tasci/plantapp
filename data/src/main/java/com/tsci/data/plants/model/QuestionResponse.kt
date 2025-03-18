package com.tsci.data.plants.model

import com.google.gson.annotations.SerializedName
import com.tsci.domain.plants.model.QuestionDomainModel

data class QuestionResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("image_uri")
    val imageUrl: String?,
    @SerializedName("uri")
    val uri: String?,
    @SerializedName("order")
    val order: Int?
)


fun QuestionResponse.toDomainModel(): QuestionDomainModel {
    return QuestionDomainModel(
        id = id ?: 0,
        title = title ?: "",
        imageUrl = imageUrl ?: ""
    )
}

fun List<QuestionResponse>.toDomainModel(): List<QuestionDomainModel> {
    return sortedBy {
        it.order
    }.map {
        it.toDomainModel()
    }
}