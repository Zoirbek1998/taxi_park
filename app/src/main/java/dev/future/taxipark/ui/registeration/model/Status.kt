package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class Status(

	@field:SerializedName("string")
	val string: String? = null,

	@field:SerializedName("int")
	val jsonMemberInt: Int? = null
)
