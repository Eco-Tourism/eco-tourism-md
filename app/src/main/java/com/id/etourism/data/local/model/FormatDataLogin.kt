package com.id.etourism.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormatDataLogin(
    val username: String? = "",
    val password: String? = ""
): Parcelable