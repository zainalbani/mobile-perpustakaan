package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetAllBookResponse(
    @SerializedName("data")
    val `data`: List<DataXX>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)