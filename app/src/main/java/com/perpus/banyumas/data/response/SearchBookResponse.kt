package com.perpus.banyumas.data.response

data class SearchBookResponse(
    val `data`: List<DataSearch>,
    val message: String,
    val status: Boolean
)