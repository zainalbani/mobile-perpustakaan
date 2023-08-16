package com.perpus.banyumas.data.request


import com.google.gson.annotations.SerializedName

data class DetailPinjamRequest(
    @SerializedName("idbuku")
    val idbuku: String,
    @SerializedName("idpinjam")
    val idpinjam: String
)