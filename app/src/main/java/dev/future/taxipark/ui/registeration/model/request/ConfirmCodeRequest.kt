package dev.future.taxipark.ui.registeration.model.request

data class ConfirmCodeRequest(
	val code: Int? = null,
	val auth_key: String? = null
)

