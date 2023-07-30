package com.perpus.banyumas.data.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("user")
    val user: User
)