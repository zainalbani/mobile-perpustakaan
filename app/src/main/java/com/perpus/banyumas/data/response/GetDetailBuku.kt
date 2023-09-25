package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetDetailBuku(
    @SerializedName("data")
    val `data`: DataXXX,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)