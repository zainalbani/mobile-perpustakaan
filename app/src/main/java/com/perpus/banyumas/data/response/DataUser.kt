package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataUser(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("idanggota")
    val idanggota: String,
    @SerializedName("idkelas")
    val idkelas: Int,
    @SerializedName("jk")
    val jk: String,
    @SerializedName("kategori")
    val kategori: String,
    @SerializedName("keterangan")
    val keterangan: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("nohp")
    val nohp: String,
    @SerializedName("pass")
    val pass: String,
    @SerializedName("status")
    val status: String
)