package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataPinjamById(
    @SerializedName("idanggota")
    val idanggota: String,
    @SerializedName("idpetugas")
    val idpetugas: String,
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tglpinjam")
    val tglpinjam: String
)