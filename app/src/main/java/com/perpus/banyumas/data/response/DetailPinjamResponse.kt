package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DetailPinjamResponse(
    @SerializedName("data")
    val `data`: DataXXXX,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)