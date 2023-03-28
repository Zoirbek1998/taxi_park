package dev.future.taxipark.ui.registeration.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("password")
	val password: String? = null,
	@field:SerializedName("phone")
	val phone: String? = null
)
