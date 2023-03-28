package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class ChangePassword(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_repeat")
	val passwordRepeat: String? = null
)
