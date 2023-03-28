package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class BonuseDriversModel(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("list")
	val list: List<ListItem?>? = null
) : Parcelable

@Parcelize
data class ListItem(

	@field:SerializedName("total_summa")
	val totalSumma: String? = null,

	@field:SerializedName("referral")
	val referral: String? = null,

	@field:SerializedName("total_bonus")
	val totalBonus: String? = null,

	@field:SerializedName("count")
	val count: String? = null
) : Parcelable
