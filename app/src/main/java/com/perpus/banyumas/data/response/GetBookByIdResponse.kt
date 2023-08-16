package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetBookByIdResponse(
    @SerializedName("data")
    val `data`: DataGetBookById,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)