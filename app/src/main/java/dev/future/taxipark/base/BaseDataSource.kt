package dev.future.taxipark.base
import dev.future.taxipark.utils.ResultData
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResultData<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ResultData.success(body)
            }
            return error("${response.code()}")
            //: ${response.message()}
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): ResultData<T> {
        Timber.e(message)
        //ApiError(message,)
        return ResultData.error(message)
    }
}