package com.tsci.data.plants.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pageCount")
    val pageCount: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("total")
    val total: Int?
)