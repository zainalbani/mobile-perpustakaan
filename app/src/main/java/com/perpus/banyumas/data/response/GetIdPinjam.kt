package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetIdPinjam(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)