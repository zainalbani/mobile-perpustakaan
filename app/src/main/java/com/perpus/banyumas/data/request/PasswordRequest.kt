package com.perpus.banyumas.data.request

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("oldPassword")
    var oldPassword: String?,
    @SerializedName("newPassword")
    var newPassword: String?,
    @SerializedName("confirmNewPassword")
    var confirmNewPassword: String?
)