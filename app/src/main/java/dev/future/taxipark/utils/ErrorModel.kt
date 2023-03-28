package dev.future.taxipark.utils

import com.google.gson.annotations.SerializedName

data class ErrorModel(
	@field:SerializedName("phone")
	val phone: List<String?>? = null,

	@field:SerializedName("first_name")
	val first_name: List<String>? = null,

	@field:SerializedName("last_name")
	val last_name: List<String?>? = null,

	@field:SerializedName("password")
	val password: List<String>? = null,

	@field:SerializedName("car_model_id")
	val car_model_id: List<String>? = null

)

