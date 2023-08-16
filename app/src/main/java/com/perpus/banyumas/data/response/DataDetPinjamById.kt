package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataDetPinjamById(
    @SerializedName("idbuku")
    val idbuku: String,
    @SerializedName("iddetpinjam")
    val iddetpinjam: Int,
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("jml_buku")
    val jmlBuku: Int,
    @SerializedName("status")
    val status: String
)