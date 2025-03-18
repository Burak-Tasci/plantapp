package com.tsci.data.plants.model


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("alternativeText")
    val alternativeText: Any?,
    @SerializedName("caption")
    val caption: Any?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("ext")
    val ext: String?,
    @SerializedName("formats")
    val formats: Any?,
    @SerializedName("hash")
    val hash: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("mime")
    val mime: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("previewUrl")
    val previewUrl: Any?,
    @SerializedName("provider")
    val provider: String?,
    @SerializedName("provider_metadata")
    val providerMetadata: Any?,
    @SerializedName("size")
    val size: Double?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)