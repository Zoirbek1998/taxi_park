package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderHistory(

	@field:SerializedName("order_history")
	val orderHistory: List<OrderItem?>? = null,

	@field:SerializedName("total")
	val total: Int? = null
) : Parcelable

@Parcelize
data class OrderItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("order_id")
	val orderId: String? = null
) : Parcelable
