package com.id.etourism.data.network.repository

import com.google.gson.annotations.SerializedName

data class WisataResponse(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Place_Name")
	val placeName: String? = null,

	@field:SerializedName("Rating")
	val rating: Any? = null,

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("Time_Minutes")
	val timeMinutes: String? = null,

	@field:SerializedName("Unnamed: 11")
	val unnamed11: String? = null,

	@field:SerializedName("Unnamed: 12")
	val unnamed12: Int? = null,

	@field:SerializedName("Place_Id")
	val placeId: Int? = null,

	@field:SerializedName("Price")
	val price: Int? = null,

	@field:SerializedName("Coordinate")
	val coordinate: String? = null,

	@field:SerializedName("Long")
	val long: Any? = null,

	@field:SerializedName("Lat")
	val lat: Any? = null
)
