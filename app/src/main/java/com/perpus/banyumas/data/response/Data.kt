package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("email")
    val email: String,
    @SerializedName("idanggota")
    val idanggota: String,
    @SerializedName("idkelas")
    val idkelas: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("pass")
    val pass: String
)