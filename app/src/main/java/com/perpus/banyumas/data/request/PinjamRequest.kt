package com.perpus.banyumas.data.request


import com.google.gson.annotations.SerializedName

data class PinjamRequest(
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("idbuku")
    val idbuku: String
)