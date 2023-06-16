package com.id.etourism.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormatDataRegister(
    val username: String? = "",
    val password: String? = "",
    val namalengkap: String? = "",
    val alamat: String? = "",
    val kontak: String? = "",
    val jeniskelamin: String? = "",
    val hobi: String? = "",
    val email: String? = "",
    val kota: Int?,
    val suasana: Int?
): Parcelable