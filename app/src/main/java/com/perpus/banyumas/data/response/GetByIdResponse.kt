package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetByIdResponse(
    @SerializedName("data")
    val `data`: DataUser,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)