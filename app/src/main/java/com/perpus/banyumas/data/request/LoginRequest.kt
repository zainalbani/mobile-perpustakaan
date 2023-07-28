package com.perpus.banyumas.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("pass")
    var pass: String?
)