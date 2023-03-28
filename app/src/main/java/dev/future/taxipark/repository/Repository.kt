package dev.future.taxipark.repository

import dev.future.taxipark.base.BaseDataSource
import dev.future.taxipark.network.ApiService
import dev.future.taxipark.ui.drawer.model.request.DayRequest
import dev.future.taxipark.ui.drawer.model.request.addCardRequest
import dev.future.taxipark.ui.drawer.model.request.driverReferralsRequest
import dev.future.taxipark.ui.drawer.model.request.moneyCreateRequest
import dev.future.taxipark.ui.registeration.model.ChangePassword
import dev.future.taxipark.ui.registeration.model.RecoverModel
import dev.future.taxipark.ui.registeration.model.request.ConfirmCodeRequest
import dev.future.taxipark.ui.registeration.model.request.LoginRequest
import dev.future.taxipark.ui.registeration.model.request.ResetRequestModel
import dev.future.taxipark.ui.registeration.model.request.registerRequest
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    private var token = "Bearer " + SaveUserInformation.getAuthInfo().authKey.toString()

    suspend fun registration(registerRequest: registerRequest) = getResult {
        apiService.registration(registerRequest)
    }

    suspend fun login(loginRequest: LoginRequest) =
        apiService.login(loginRequest)


    suspend fun getMe(token: String) = getResult {
        apiService.getMe("Bearer $token")
    }

    suspend fun confirmSmsCode(confirmCodeRequest: ConfirmCodeRequest) = getResult {
        apiService.userConfirm(confirmCodeRequest)
    }

    suspend fun nurseFullData(
        auth_key: RequestBody,
        first_name: RequestBody,
        last_name: RequestBody,
        middle_name: RequestBody,
        image_1: MultipartBody.Part,
        image_2: MultipartBody.Part,
        image_3: MultipartBody.Part,
        image_4: MultipartBody.Part,
    ) = getResult {
        apiService.fullNurseData(
            auth_key,
            first_name,
            last_name,
            middle_name,
            image_1,
            image_2,
            image_3,
            image_4,
        )
    }

    suspend fun sendSmsNumber(resetRequestModel: ResetRequestModel) = getResult {
        apiService.sendSmsNumber(resetRequestModel)
    }

    suspend fun changePassword(token: String, changePassword: ChangePassword) = getResult {
        apiService.changePassword("Bearer $token", changePassword)
    }

    suspend fun recoverPassword(codeRequest: RecoverModel) = getResult {
        apiService.recoverPassword(codeRequest)
    }

    suspend fun getSlider() = getResult {
        apiService.getSlider()
    }

    suspend fun getAudio() = getResult {
        apiService.getAudio()
    }

    suspend fun addCards(token: String,addCard: addCardRequest)=getResult {
        apiService.addCard("Bearer $token",addCard)
    }

    suspend fun getCards(token: String)=getResult {
        apiService.getCards("Bearer $token")
    }

    suspend fun getBalanseDetaile(token: String)=getResult {
        apiService.getBalanceDetaile("Bearer $token")
    }

    suspend fun getMyBrigada(token: String)=getResult {
        apiService.getMyBrigada("Bearer $token")
    }

    suspend fun cardDelete(token: String,card_id:Int) = getResult {
        apiService.cardDelete("Bearer $token",card_id)
    }

    suspend fun cardUpdate(token: String,cardCard: addCardRequest,card_id:Int)=getResult {
        apiService.cardUpdate("Bearer $token",cardCard,card_id)
    }

    suspend fun moneyTaking(token: String)=getResult {
        apiService.cardMoneyTaking("Bearer $token")
    }

    suspend fun createMoney(token: String,createMonenyRequest: moneyCreateRequest)=getResult {
        apiService.moneyCreate("Bearer $token",createMonenyRequest)
    }

    suspend fun bonusBalanse(token: String)=getResult {
        apiService.bonusBalanse("Bearer $token")
    }

    suspend fun bonusDriver(token: String,day:String)=getResult {
        apiService.bonusDrivers("Bearer $token",day)
    }

    suspend fun bonuseMoneyTaking(token: String)=getResult {
        apiService.bonusMoneyTaking("Bearer $token")
    }

    suspend fun bonuseCreateMoney(token: String,createMonenyRequest: moneyCreateRequest)=getResult {
        apiService.binuseMoneyCreate("Bearer $token",createMonenyRequest)
    }

    suspend fun orderHistory(token: String)=getResult {
        apiService.ordersHistory("Bearer $token")
    }

    suspend fun orderBalanse(token: String,day: DayRequest)=getResult {
        apiService.ordersBalanseHistory("Bearer $token",day)
    }

    suspend fun driverReferrals(token: String,phone: driverReferralsRequest)=getResult {
        apiService.driverReferrals("Bearer $token",phone)
    }

    suspend fun driverMe(token: String)=getResult {
        apiService.driverMe("Bearer $token")
    }
}