package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderHistoryTravel(

	@field:SerializedName("order_history")
	val orderHistory: List<OrderHistoryItem?>? = null
) : Parcelable

@Parcelize
data class OrderHistoryItem(

	@field:SerializedName("total_summa")
	val totalSumma: String? = null,

	@field:SerializedName("first_created_at")
	val firstCreatedAt: String? = null,

	@field:SerializedName("last_created_at")
	val lastCreatedAt: String? = null,

	@field:SerializedName("count")
	val count: String? = null,

	@field:SerializedName("day")
	val day: String? = null
) : Parcelable
