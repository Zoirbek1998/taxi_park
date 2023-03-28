package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MyBrigadaModel(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("referal_id")
	val referalId: ReferalId? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("status")
	val status: StatusBrigada? = null
) : Parcelable

@Parcelize
data class ReferalId(

	@field:SerializedName("driver")
	val driver: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null
) : Parcelable

@Parcelize
data class StatusBrigada(

	@field:SerializedName("string")
	val string: String? = null,

	@field:SerializedName("int")
	val jsonMemberInt: Int? = null
) : Parcelable
