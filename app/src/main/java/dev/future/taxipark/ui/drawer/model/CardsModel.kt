package dev.future.taxipark.ui.drawer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardsModel(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("card_number")
	val cardNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	var isExpandable:Boolean = false
): Parcelable