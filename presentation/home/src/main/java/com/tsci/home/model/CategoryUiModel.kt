package com.tsci.home.model

import com.tsci.domain.plants.model.CategoryDomainModel

data class CategoryUiModel(
    val name: String,
    val imageUrl: String
)


fun CategoryDomainModel.toUiModel(): CategoryUiModel {
    val wrappedTitle = title.replaceFirst(
        " ", "\n"
    )

    return CategoryUiModel(
        name = wrappedTitle,
        imageUrl = imageUrl
    )
}