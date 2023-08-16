package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataDetailPinjam(
    @SerializedName("idbuku")
    val idbuku: String,
    @SerializedName("iddetpinjam")
    val iddetpinjam: Any,
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("jml_buku")
    val jmlBuku: String
)