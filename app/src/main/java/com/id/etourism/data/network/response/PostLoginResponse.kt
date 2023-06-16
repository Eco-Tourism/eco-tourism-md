package com.id.etourism.data.network.response

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(

	@field:SerializedName("kontak")
	val kontak: String? = null,

	@field:SerializedName("jeniskelamin")
	val jeniskelamin: String? = null,

	@field:SerializedName("namalengkap")
	val namalengkap: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("hobi")
	val hobi: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("kota")
	val kota: Int? = null,

	@field:SerializedName("suasana")
	val suasana: Int? = null
)
