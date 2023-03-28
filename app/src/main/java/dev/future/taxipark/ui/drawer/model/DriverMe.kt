package dev.future.taxipark.ui.drawer.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DriverMe(

	@field:SerializedName("driver")
	val driver: Driver? = null
) : Parcelable

@Parcelize
data class Driver(

	@field:SerializedName("dl_photo_first")
	val dlPhotoFirst: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birth_date")
	val birthDate: String? = null,

	@field:SerializedName("vehicle_model")
	val vehicleModel: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("middle_name")
	val middleName: String? = null,

	@field:SerializedName("vehicle_brand")
	val vehicleBrand: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("vehicle_number")
	val vehicleNumber: String? = null,

	@field:SerializedName("vl_photo_second")
	val vlPhotoSecond: String? = null,

	@field:SerializedName("dl_photo_second")
	val dlPhotoSecond: String? = null,

	@field:SerializedName("vehicle_licence")
	val vehicleLicence: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("driver_license")
	val driverLicense: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("vl_photo_first")
	val vlPhotoFirst: String? = null
) : Parcelable
