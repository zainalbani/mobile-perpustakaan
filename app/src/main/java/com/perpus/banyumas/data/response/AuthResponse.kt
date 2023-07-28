package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val msg: String,
    @SerializedName("status")
    val status: String
)