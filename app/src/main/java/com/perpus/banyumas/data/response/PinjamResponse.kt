package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class PinjamResponse(
    @SerializedName("data")
    val `data`: DataXXX,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)