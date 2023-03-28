package dev.future.taxipark.utils

data class ResultData<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?=null): ResultData<T> =
            ResultData(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(message: String, data: T? = null): ResultData<T> =
            ResultData(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T? = null): ResultData<T> =
            ResultData(status = Status.LOADING, data = data, message = null)
    }

}