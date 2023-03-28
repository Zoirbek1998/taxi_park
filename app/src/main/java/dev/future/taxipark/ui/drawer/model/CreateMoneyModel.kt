package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateMoneyModel(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("summa")
	val summa: String? = null,

	@field:SerializedName("card_id")
	val cardId: Int? = null
) : Parcelable
