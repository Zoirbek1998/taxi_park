package dev.future.taxipark.ui.registeration.model

import com.google.gson.annotations.SerializedName

data class BlockUser(

	@field:SerializedName("lifetime")
	val lifetime: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("until")
	val until: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
