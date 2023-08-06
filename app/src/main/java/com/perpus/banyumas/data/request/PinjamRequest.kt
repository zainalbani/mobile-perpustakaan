package com.perpus.banyumas.data.request


import com.google.gson.annotations.SerializedName

data class PinjamRequest(
    @SerializedName("idanggota")
    val idanggota: String,
    @SerializedName("idpetugas")
    val idpetugas: String,
    @SerializedName("idpinjam")
    val idpinjam: String,
    @SerializedName("tglpinjam")
    val tglpinjam: String
)