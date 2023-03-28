package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class AudioModel(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("info")
	val info: String? = null
)
