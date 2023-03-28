package dev.future.taxipark.base

import com.google.gson.annotations.SerializedName
import dev.future.taxipark.utils.ErrorModel


class BaseResponse<T>(
    var success: Boolean,
    var data: T? = null,
    var status: Int? = null,
    var message: String?=null,
    var error: ErrorModel? = null
)
class BaseResponseOrder<T>(
    @field:SerializedName("data")
    val data:T? = null,
    @field:SerializedName("success")
    val success: Boolean=true,
    @field:SerializedName("message")
    val message: String? = null)

class BaseResponse1(
    @field:SerializedName("data")
    val data: List<Any?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("errors")
    val errors: List<Any?>? = null,

    @field:SerializedName("status")
    val status: Int? = null
)
