package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class DataAllBook(
    @SerializedName("idbuku")
    val idbuku: String,
    @SerializedName("idkatbuku")
    val idkatbuku: String,
    @SerializedName("idrak")
    val idrak: String,
    @SerializedName("jml")
    val jml: Int,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("keterangan")
    val keterangan: String,
    @SerializedName("penerbit")
    val penerbit: String,
    @SerializedName("pengarang")
    val pengarang: String,
    @SerializedName("thnterbit")
    val thnterbit: Int
)