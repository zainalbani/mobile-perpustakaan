package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class GetDetailPinjamByIdResponse(
    @SerializedName("data")
    val `data`: List<DataDetPinjamById>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)