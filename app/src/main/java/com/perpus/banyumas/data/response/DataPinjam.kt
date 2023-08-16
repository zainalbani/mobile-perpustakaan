package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataPinjam(
    @SerializedName("idanggota")
    val idanggota: String,
    @SerializedName("idpetugas")
    val idpetugas: String,
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("tglpinjam")
    val tglpinjam: String,
    @SerializedName("status")
    val status: String
)