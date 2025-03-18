package com.tsci.home.model

import com.tsci.domain.plants.model.QuestionDomainModel

data class QuestionUiModel(
    val question: String,
    val imageUrl: String
)

fun QuestionDomainModel.toUiModel(): QuestionUiModel {
    return QuestionUiModel(
        question = title,
        imageUrl = imageUrl
    )
}
