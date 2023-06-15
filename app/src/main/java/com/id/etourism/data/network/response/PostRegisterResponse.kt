package com.id.etourism.data.network.response

import com.google.gson.annotations.SerializedName

data class PostRegisterResponse(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("namalengkap")
    val namalengkap: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("kontak")
    val kontak: String? = null,

    @field:SerializedName("jeniskelamin")
    val jeniskelamin: String? = null,

    @field:SerializedName("hobi")
    val hobi: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

)