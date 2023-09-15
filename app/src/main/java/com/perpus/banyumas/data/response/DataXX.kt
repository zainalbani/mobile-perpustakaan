package com.perpus.banyumas.data.response

data class DataXX(
    val buku: Buku,
    val idbuku: String,
    val iddetpinjam: Int,
    val idpinjam: String,
    val jml_buku: Int,
    val pinjam: Pinjam,
    val status: String
)