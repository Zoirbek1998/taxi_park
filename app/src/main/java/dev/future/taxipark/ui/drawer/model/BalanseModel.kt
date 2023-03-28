package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

const val NEW = 1
const val PROCESS = 2
const val CONFIRM = 3
const val CANCEL = -1

@Parcelize
data class BalanseModel(

	@field:SerializedName("transactions")
	val transactions: List<TransactionsItem?>? = null,

	@field:SerializedName("balans")
	val balans: Int? = null
) : Parcelable

@Parcelize
data class Source(

	@field:SerializedName("string")
	val string: String? = null,

	@field:SerializedName("int")
	val jsonMemberInt: Int? = null
) : Parcelable

@Parcelize
data class TransactionsItem(

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("summa")
	val summa: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("type")
	val type: Type? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("status")
	val status: Status? = null
) : Parcelable

@Parcelize
data class Type(

	@field:SerializedName("string")
	val string: String? = null,

	@field:SerializedName("int")
	val jsonMemberInt: Int? = null
) : Parcelable

@Parcelize
data class Status(

	@field:SerializedName("string")
	val string: String? = null,

	@field:SerializedName("int")
	val jsonMemberInt: Int? = null
) : Parcelable
