package com.perpus.banyumas.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val name: String,
    val photo: Int
): Parcelable
