package com.tsci.data.sample.model

import com.tsci.domain.sample.model.SampleDomainModel

data class SampleResponse(
    val name: String
)


fun SampleResponse.toDomainModel(): SampleDomainModel {
    return SampleDomainModel(
        name = name
    )
}