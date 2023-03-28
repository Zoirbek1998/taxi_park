package dev.future.taxipark.network

import dev.future.taxipark.base.BaseResponse
import dev.future.taxipark.base.BaseResponse1
import dev.future.taxipark.ui.drawer.model.*
import dev.future.taxipark.ui.drawer.model.request.DayRequest
import dev.future.taxipark.ui.drawer.model.request.addCardRequest
import dev.future.taxipark.ui.drawer.model.request.driverReferralsRequest
import dev.future.taxipark.ui.drawer.model.request.moneyCreateRequest
import dev.future.taxipark.ui.registeration.model.*
import dev.future.taxipark.ui.registeration.model.request.ConfirmCodeRequest
import dev.future.taxipark.ui.registeration.model.request.LoginRequest
import dev.future.taxipark.ui.registeration.model.request.ResetRequestModel
import dev.future.taxipark.ui.registeration.model.request.registerRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    companion object {
        const val SERVER = "temp.four-seasons.uz"
        const val BASE_URL = "https://$SERVER/api/v1/"
        const val IMAGE_URL = "https://$SERVER"
    }

    @POST("user/register")
    suspend fun registration(@Body registerRequest: registerRequest): Response<BaseResponse<AuthModel>>

    @POST("user/confirm")
    suspend fun userConfirm(@Body confirmCodeRequest: ConfirmCodeRequest): Response<BaseResponse<AuthModel>>

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): BaseResponse<AuthModel>

    @GET("user/me")
    suspend fun getMe(@Header("Authorization") auth: String): Response<BaseResponse<AuthModel>>

    @Multipart
    @POST("ser/fill-data")
    suspend fun fullNurseData(
        @Part("auth_key") auth_key: RequestBody,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("middle_name") middle_name: RequestBody,
        @Part image_1: MultipartBody.Part,
        @Part image_2: MultipartBody.Part,
        @Part image_3: MultipartBody.Part,
        @Part image_4: MultipartBody.Part,
    ): Response<BaseResponse<AuthModel>>

    @POST("user/send-sms")
    suspend fun sendSmsNumber(@Body resetRequestModel: ResetRequestModel): Response<BaseResponse1>

    @POST("user/change-password")
    suspend fun changePassword(
        @Header("Authorization") auth: String,
        @Body changePassword: ChangePassword
    ): Response<BaseResponse<ArrayList<AuthModel>>>

    @POST("user/recover")
    suspend fun recoverPassword(@Body confirmCodeRequest: RecoverModel): Response<BaseResponse<ArrayList<AuthModel>>>

    @GET("slider")
    suspend fun getSlider(): Response<BaseResponse<ArrayList<SliderModel>>>

    @GET("welcome-audio")
    suspend fun getAudio(): Response<BaseResponse<AudioModel>>

    @POST("cards/create")
    suspend fun addCard(
        @Header("Authorization") auth: String,
        @Body addCardRequest: addCardRequest
    ): Response<BaseResponse<CardsModel>>

    @GET("cards/index")
    suspend fun getCards(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<ArrayList<CardsModel>>>

    @GET("transactions/balance")
    suspend fun getBalanceDetaile(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<BalanseModel>>

    @GET("driver-referrals/driver")
    suspend fun getMyBrigada(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<ArrayList<MyBrigadaModel>>>

    @POST("cards/delete")
    suspend fun cardDelete(
        @Header("Authorization") auth: String,
        @Query("card_id") card_id: Int
    ): Response<BaseResponse<String>>

    @POST("cards/update")
    suspend fun cardUpdate(
        @Header("Authorization") auth: String,
        @Body addCardRequest: addCardRequest,
        @Query("card_id") id: Int
    ): Response<BaseResponse<addCardRequest>>

    @GET("transactions/prepare-data")
    suspend fun cardMoneyTaking(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<MoneyTaking>>

    @POST("transactions/create")
    suspend fun moneyCreate(
        @Header("Authorization") auth: String,
        @Body moneyCreateRequest: moneyCreateRequest
    ): Response<BaseResponse<ArrayList<CreateMoneyModel>>>

    @GET("bonus/bonus-balance")
    suspend fun bonusBalanse(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<BonusBalanse>>

    @GET("bonus/view")
    suspend fun bonusDrivers(
        @Header("Authorization") auth: String,
        @Query("day") day: String
    ): Response<BaseResponse<BonuseDriversModel>>

    @GET("bonus/prepare-data")
    suspend fun bonusMoneyTaking(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<MoneyTaking>>

    @POST("bonus/create")
    suspend fun binuseMoneyCreate(
        @Header("Authorization") auth: String,
        @Body moneyCreateRequest: moneyCreateRequest
    ): Response<BaseResponse<ArrayList<CreateMoneyModel>>>

    @GET("orders/history")
    suspend fun ordersHistory(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<OrderHistoryTravel>>

    @GET("orders/balance-history")
    suspend fun ordersBalanseHistory(
        @Header("Authorization") auth: String,
        @Query("day") day: DayRequest
    ): Response<BaseResponse<OrderHistory>>

    @POST("driver-referrals/create")
    suspend fun driverReferrals(
        @Header("Authorization") auth: String,
        @Body phone: driverReferralsRequest
    ): Response<BaseResponse<ArrayList<CreateMoneyModel>>>

    @GET("drivers/me")
    suspend fun driverMe(
        @Header("Authorization") auth: String,
    ): Response<BaseResponse<DriverMe>>
}