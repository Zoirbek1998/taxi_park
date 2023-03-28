package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MoneyTaking(

	@field:SerializedName("cards")
	val cards: List<CardsItem?>? = null,

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("limits")
	val limits: Limits? = null
) : Parcelable

@Parcelize
data class CardsItem(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("card_number")
	val cardNumber: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Int? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	var isExpandable:Boolean = false,

	var chacked:Boolean = false
) : Parcelable

@Parcelize
data class Limits(

	@field:SerializedName("max_limit")
	val maxLimit: Int? = null,

	@field:SerializedName("min_balance")
	val minBalance: Int? = null,

	@field:SerializedName("min_limit")
	val minLimit: Int? = null,

	@field:SerializedName("comission")
	val comission: Int? = null,

	@field:SerializedName("min_comission")
	val minComission: Int? = null,

	@field:SerializedName("daily_limit")
	val dailyLimit: Int? = null
) : Parcelable
