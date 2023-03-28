package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class SliderModel(

	@field:SerializedName("header")
	val header: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
