package com.perpus.banyumas.data.response

data class DataSearch(
    val barcode: String,
    val idbuku: String,
    val idkatbuku: String,
    val idrak: String,
    val jml: Int,
    val judul: String,
    val keterangan: String,
    val penerbit: String,
    val pengarang: String,
    val thnterbit: Int
)