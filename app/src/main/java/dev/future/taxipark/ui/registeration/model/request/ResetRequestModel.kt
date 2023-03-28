package dev.future.taxipark.ui.registeration.model.request

import com.google.gson.annotations.SerializedName

data class ResetRequestModel(

	@field:SerializedName("phone")
	val phone: String? = null
)
