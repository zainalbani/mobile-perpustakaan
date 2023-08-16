package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetPinjamByIdResponse(
    @SerializedName("data")
    val `data`: List<DataPinjamById>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)