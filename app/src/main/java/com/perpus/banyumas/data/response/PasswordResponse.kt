package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class PasswordResponse(
    @SerializedName("data")
    val `data`: List<Int>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean

)