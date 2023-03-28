package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class RecoverModel(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null
)
