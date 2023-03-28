package dev.future.taxipark.ui.auth.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import dev.future.taxipark.base.BaseResponse
import dev.future.taxipark.base.BaseResponse1
import dev.future.taxipark.base.BaseViewModel
import dev.future.taxipark.base.SingleLiveEvent
import dev.future.taxipark.repository.Repository
import dev.future.taxipark.ui.SplashFragment
import dev.future.taxipark.ui.registeration.model.*
import dev.future.taxipark.ui.registeration.model.request.*
import dev.future.taxipark.utils.ResultData
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Named

class AuthViewmodel @Inject constructor(
    application: Application,
    private val repository: Repository,
    @Named("IO") private val io: CoroutineDispatcher,
    @Named("MAIN") private val main: CoroutineDispatcher
) : BaseViewModel(application)
{

    private val _timerFlow: MutableStateFlow<String> = MutableStateFlow("")
    val timerFlow: StateFlow<String> get() = _timerFlow.asStateFlow()

    private val _timerSaveFlow: MutableStateFlow<Long> = MutableStateFlow(0)
    val timerSaveFlow: StateFlow<Long> get() = _timerSaveFlow.asStateFlow()

    private val _isStartFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isStartFlow: StateFlow<Boolean> get() = _isStartFlow.asStateFlow()

    private val _isEndFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEndFlow: StateFlow<Boolean> get() = _isEndFlow.asStateFlow()

    private val _registr = SingleLiveEvent<ResultData<BaseResponse<AuthModel>>>()
    val register: SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> get() = _registr

    private fun setResultRegister(result: ResultData<BaseResponse<AuthModel>>) {
        _registr.postValue(result)
    }

    internal fun registration(registerRequest: registerRequest): SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> {
        viewModelScope.launch(main) {
            try {
                _registr.postValue(ResultData.loading())
                delay(10)
                val result = async(context = io) {
                    repository.registration(registerRequest)
                }
                if (result.await().data?.success == true) {
                    SaveUserInformation.saveAuthInfo(ResultData.success(result.await()).data?.data?.data!!)
                }
                _registr.postValue(ResultData.success(result.await().data))
            } catch (e: Throwable) {
                _registr.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
        return register
    }

    private var _login = SingleLiveEvent<ResultData<BaseResponse<AuthModel>>>()
    val login: SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> get() = _login

    private fun setResultLogin(result: ResultData<BaseResponse<AuthModel>>) {
        _login.postValue(result)
    }

    fun getlogin(loginRequest: LoginRequest): SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> {
        viewModelScope.launch(io) {
            _login.postValue(ResultData.loading())
            try {
                val result = repository.login(loginRequest)
                _login.postValue(ResultData.success(result))
                if (result.success) {
                    SaveUserInformation.saveAuthInfo(ResultData.success(result).data?.data!!)

                }
            } catch (e: Throwable) {
                _login.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
        return login
    }

    private var _getMe = SingleLiveEvent<ResultData<BaseResponse<AuthModel>>>()
    val getMe: SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> get() = _getMe

    private fun setResultMe(result: ResultData<BaseResponse<AuthModel>>) {
        _getMe.postValue(result)
    }

    fun funcgetMe(token: String) {
        viewModelScope.launch(main) {
            setResultMe(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.getMe(token)
                    }
                setResultMe(ResultData.success(result.await()).data!!)

            } catch (e: Throwable) {
                setResultMe(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }



    private var _confirmSms = SingleLiveEvent<ResultData<BaseResponse<AuthModel>>>()
    val confirmSms: SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> get() = _confirmSms

    private fun setResult(result: ResultData<BaseResponse<ConfirmCodeResponse>>) {
        // _killSocket.postValue(result)
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun confirmSms(confirmCodeRequest: ConfirmCodeRequest): SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> {
        viewModelScope.launch(main) {
            _confirmSms.postValue(ResultData.loading())
            try {
                val result = async(context = io) {
                    repository.confirmSmsCode(confirmCodeRequest)
                }
                _confirmSms.postValue(ResultData.success(result.await()).data)
                if (result.await().data?.success == true) {
                    var datas = result.await().data!!.data
                    SaveUserInformation.saveAuthInfo(
                        AuthModel(
                            authKey = datas?.authKey,
                            firstName = datas?.firstName,
                            lastName = datas?.lastName,
                            id = datas?.id,
                            username = datas?.firstName
                        )
                    )
                }

            } catch (e: Throwable) {
                _confirmSms.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }

        return confirmSms
    }


    private var _fullNurseData = SingleLiveEvent<ResultData<BaseResponse<AuthModel>>>()
    val fullNurseData: SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> get() = _fullNurseData

    fun fullNurseData(
        auth_key: RequestBody,
        first_name: RequestBody,
        last_name: RequestBody,
        middle_name: RequestBody,
        image_1: MultipartBody.Part,
        image_2: MultipartBody.Part,
        image_3: MultipartBody.Part,
        image_4: MultipartBody.Part,
    ): SingleLiveEvent<ResultData<BaseResponse<AuthModel>>> {
        viewModelScope.launch(main) {
            _fullNurseData.postValue(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.nurseFullData(
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
                _fullNurseData.postValue(ResultData.success(result.await()).data!!)
                if (result.await().data?.success == true) {
                    var datas = result.await().data!!
                    SaveUserInformation.saveAuthInfo(datas.data!!)
                }

            } catch (e: Throwable) {
                _fullNurseData.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }

        return fullNurseData
    }

    private var _sendNumberSMS = SingleLiveEvent<ResultData<BaseResponse1>>()
    val sendNumberSMS: SingleLiveEvent<ResultData<BaseResponse1>> get() = _sendNumberSMS

    @SuppressLint("NullSafeMutableLiveData")
    fun sendSmsNumber(part: ResetRequestModel): SingleLiveEvent<ResultData<BaseResponse1>> {
        viewModelScope.launch(main) {
            _sendNumberSMS.postValue(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.sendSmsNumber(part)
                    }
                _sendNumberSMS.postValue(ResultData.success(result.await()).data)
                if (result.await().data?.success == true) {

                }

            } catch (e: Throwable) {
                _sendNumberSMS.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
        return sendNumberSMS
    }


    private var _changePassword = SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>>()
    val changePassword: SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>> get() = _changePassword

    fun changePassword(
        token: String,
        changePassword1: ChangePassword
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>> {
        viewModelScope.launch(main) {
            _changePassword.postValue(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.changePassword(token, changePassword1)
                    }
                _changePassword.postValue(ResultData.success(result.await()).data!!)
                if (result.await().data?.success == true) {
                    var datas = result.await().data!!
                    SaveUserInformation.saveAuthInfo(datas.data!![0])
                }

            } catch (e: Throwable) {
                _changePassword.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
        return changePassword
    }

    private var _recoverPassword = SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>>()
    val recoverPassword: SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>> get() = _recoverPassword

    @SuppressLint("NullSafeMutableLiveData")
    fun recoverPassword(recoverModel: RecoverModel): SingleLiveEvent<ResultData<BaseResponse<ArrayList<AuthModel>>>> {
        viewModelScope.launch(main) {
            _recoverPassword.postValue(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.recoverPassword(recoverModel)
                    }
                _recoverPassword.postValue(ResultData.success(result.await()).data)
                if (result.await().data?.success == true) {
                    // var datas = result.await().data!!
                    // SaveUserInformation.saveAuthInfo(datas.data!!)
                }
            } catch (e: Throwable) {
                _recoverPassword.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }
        return recoverPassword
    }

        private var _getSlider = SingleLiveEvent<ResultData<BaseResponse<ArrayList<SliderModel>>>>()
    val getSlider: SingleLiveEvent<ResultData<BaseResponse<ArrayList<SliderModel>>>> get() = _getSlider

    fun getSlider(): SingleLiveEvent<ResultData<BaseResponse<ArrayList<SliderModel>>>> {
        viewModelScope.launch(main) {
            _getSlider.postValue(ResultData.loading())
            try {
                val result =
                    async(context = io) {
                        repository.getSlider()
                    }
                _getSlider.postValue(ResultData.success(result.await()).data!!)
                if (result.await().data?.success == true) {
                    // var datas = result.await().data!!
                    // SaveUserInformation.saveAuthInfo(datas.data!!)
                }
            } catch (e: Throwable) {
                _getSlider.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }

        return getSlider
    }

    private var _getAudio = SingleLiveEvent<ResultData<BaseResponse<AudioModel>>>()
    val getAudio: SingleLiveEvent<ResultData<BaseResponse<AudioModel>>> get() = _getAudio

    fun getAudio(): SingleLiveEvent<ResultData<BaseResponse<AudioModel>>> {
        viewModelScope.launch(main) {
            _getAudio.postValue(ResultData.loading())
            try {
                val result = async(context = io) {
                    repository.getAudio()
                }
                _getAudio.postValue(ResultData.success(result.await()).data!!)
                if (result.await().data?.success == true) {
                    // var datas = result.await().data!!
                    // SaveUserInformation.saveAuthInfo(datas.data!!)
                }
            } catch (e: Throwable) {
                _getAudio.postValue(ResultData.error(e.localizedMessage ?: "Unknown error"))
            }
        }

        return getAudio
    }


//    private var _getSpecialityNurse =
//        SingleLiveEvent<Result<BaseResponse<ArrayList<SpecailityModel>>>?>()
//    val getSpecialityNurse: SingleLiveEvent<Result<BaseResponse<ArrayList<SpecailityModel>>>?> get() = _getSpecialityNurse
//
//    fun getCategoryNurse(
//        token: String,
//        language: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<SpecailityModel>>>?> {
//        viewModelScope.launch(main) {
//            _getSpecialityNurse.postValue(Result.loading())
//            try {
//                val result =
//                    async(context = io) {
//                        repository.getCategoryNurse(token, language)
//                    }
//                _getSpecialityNurse.postValue(Result.success(result.await()).data)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getSpecialityNurse.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//        return getSpecialityNurse
//    }
//
//    private var _getBranch = SingleLiveEvent<Result<BaseResponse<ArrayList<BranchModel>>>?>()
//    val getBranch: SingleLiveEvent<Result<BaseResponse<ArrayList<BranchModel>>>?> get() = _getBranch
//
//    fun getBranch(
//        token: String,
//        lang: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<BranchModel>>>?> {
//        viewModelScope.launch(main) {
//            _getBranch.postValue(Result.loading())
//            try {
//                val result =
//                    async(context = io) {
//                        repository.getBranch(token, lang)
//                    }
//                _getBranch.postValue(Result.success(result.await()).data)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getBranch.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//        return getBranch
//    }
//
//    private var _getModelCars = SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?>()
//    val getModelCars: SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?> get() = _getModelCars
//
//    fun geModelCarsMarka(
//        token: String,
//        lang: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?> {
//        viewModelScope.launch(main) {
//            _getModelCars.postValue(Result.loading())
//            try {
//                val result =
//                    async(context = io) {
//                        repository.getModelCars(token, lang)
//                    }
//                _getModelCars.postValue(Result.success(result.await()).data)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getModelCars.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//        return getModelCars
//    }
//
//    private var _getModel = SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?>()
//    val getModel: SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?> get() = _getModel
//
//    fun getModel(
//        token: String,
//        lang: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<ModelCars>>>?> {
//        viewModelScope.launch(main) {
//            _getModel.postValue(Result.loading())
//            try {
//                val result =
//                    async(context = io) {
//                        repository.getModel(token, lang)
//                    }
//                _getModel.postValue(Result.success(result.await()).data)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getModel.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//        return getModel
//    }
//
//    private var _getColor = SingleLiveEvent<Result<BaseResponse<ArrayList<Color>>>?>()
//    val getColor: SingleLiveEvent<Result<BaseResponse<ArrayList<Color>>>?> get() = _getColor
//
//    fun getColor(
//        token: String,
//        lang: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<Color>>>?> {
//        viewModelScope.launch(main) {
//            _getColor.postValue(Result.loading())
//            try {
//                val result =
//                    async(context = io) {
//                        repository.getColor(token, lang)
//                    }
//                _getColor.postValue(Result.success(result.await()).data)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getColor.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//        return getColor
//    }
//
//
//
//    private var _getVideo =
//        SingleLiveEvent<Result<BaseResponse<ArrayList<ResponseVideoInstruction>>>>()
//    val getVideo: SingleLiveEvent<Result<BaseResponse<ArrayList<ResponseVideoInstruction>>>> get() = _getVideo
//
//    fun getVideo(): SingleLiveEvent<Result<BaseResponse<ArrayList<ResponseVideoInstruction>>>> {
//        viewModelScope.launch(main) {
//            _getVideo.postValue(Result.loading())
//            try {
//                val result = async(context = io) {
//                    repository.getVideoInstruction()
//                }
//                _getVideo.postValue(Result.success(result.await()).data!!)
//                if (result.await().data?.success == true) {
//                    // var datas = result.await().data!!
//                    // SaveUserInformation.saveAuthInfo(datas.data!!)
//                }
//            } catch (e: Throwable) {
//                _getVideo.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//
//        return getVideo
//    }
//
//    private var _getNotification =
//        SingleLiveEvent<Result<BaseResponse<ArrayList<NotificationModel>>>>()
//    val getNotification: SingleLiveEvent<Result<BaseResponse<ArrayList<NotificationModel>>>> get() = _getNotification
//
//    fun getNotification(
//        token: String,
//        lang: String
//    ): SingleLiveEvent<Result<BaseResponse<ArrayList<NotificationModel>>>> {
//        viewModelScope.launch(main) {
//            _getNotification.postValue(Result.loading())
//            try {
//                val result = async(context = io) {
//                    repository.getNotification(token, lang)
//                }
//                _getNotification.postValue(Result.success(result.await()).data!!)
//
//            } catch (e: Throwable) {
//                _getNotification.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//
//        return getNotification
//    }
//
//
//    private var _getLicense = SingleLiveEvent<Result<BaseResponse<GetLicenseModel>>>()
//    val getLicense: SingleLiveEvent<Result<BaseResponse<GetLicenseModel>>> get() = _getLicense
//
//    fun getLicense(lang: String): SingleLiveEvent<Result<BaseResponse<GetLicenseModel>>> {
//        viewModelScope.launch(main) {
//            _getLicense.postValue(Result.loading())
//            try {
//                val result = async(context = io) {
//                    repository.getLicense(lang)
//                }
//                _getLicense.postValue(Result.success(result.await()).data!!)
//
//            } catch (e: Throwable) {
//                _getLicense.postValue(Result.error(e.localizedMessage ?: "Unknown error"))
//            }
//        }
//
//        return getLicense
//    }


    fun timerStart(minut: Long): CountDownTimer {
        _isStartFlow.value = true
        _isEndFlow.value = false

        //      _timerFlow.resetReplayCache()
        val timer: CountDownTimer = object : CountDownTimer(minut, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = (millisUntilFinished / 1000) % 60
                val min = (millisUntilFinished / (1000 * 60)) % 60
                val formattedTimeStr = if (sec <= 9) {
                    "0$min : 0$sec"
                } else {
                    "0$min : $sec"
                }
                Log.e("onTickTimer:", formattedTimeStr.toString())
                try {
                    SplashFragment.TimerMinut = millisUntilFinished
                    //  _timerSaveFlow.value=millisUntilFinished
                    _timerFlow.value = formattedTimeStr
                } catch (e: Exception) {
                }
            }

            override fun onFinish() {
                _isEndFlow.value = true
            }
        }
        return timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}