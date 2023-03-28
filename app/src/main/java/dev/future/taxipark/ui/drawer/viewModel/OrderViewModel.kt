package dev.future.taxipark.ui.drawer.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import dev.future.taxipark.base.BaseResponse
import dev.future.taxipark.base.BaseViewModel
import dev.future.taxipark.base.SingleLiveEvent
import dev.future.taxipark.repository.Repository
import dev.future.taxipark.ui.drawer.model.*
import dev.future.taxipark.ui.drawer.model.request.DayRequest
import dev.future.taxipark.ui.drawer.model.request.addCardRequest
import dev.future.taxipark.ui.drawer.model.request.driverReferralsRequest
import dev.future.taxipark.ui.drawer.model.request.moneyCreateRequest
import dev.future.taxipark.utils.ResultData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class OrderViewModel@Inject constructor(
    application: Application,
    private val repository: Repository,
    @Named("IO") private val io: CoroutineDispatcher,
    @Named("MAIN") private val main: CoroutineDispatcher
) : BaseViewModel(application) {
    var context = application.applicationContext


    private var _addCards =
        SingleLiveEvent<ResultData<BaseResponse<CardsModel>>>()

    val addCards: SingleLiveEvent<ResultData<BaseResponse<CardsModel>>> =
        _addCards

    internal fun addCards(
        token: String,
        addCard: addCardRequest,
    ): SingleLiveEvent<ResultData<BaseResponse<CardsModel>>>{
        viewModelScope.launch(main) {
            _addCards.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.addCards(token,addCard)
                    _addCards.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _addCards.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return addCards
    }

    private var _getCards =
        SingleLiveEvent<ResultData<BaseResponse<ArrayList<CardsModel>>>>()

    val getCards: SingleLiveEvent<ResultData<BaseResponse<ArrayList<CardsModel>>>> =
        _getCards

    internal fun getCards(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<CardsModel>>>> {
        viewModelScope.launch(main) {
            _getCards.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.getCards(token)
                    _getCards.postValue(ResultData.success(result).data!!)
                }
            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _getCards.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }
        return getCards
    }

    private var _getBalanseDetaile =
        SingleLiveEvent<ResultData<BaseResponse<BalanseModel>>>()

    val getBalanseDetaile: SingleLiveEvent<ResultData<BaseResponse<BalanseModel>>> =
        _getBalanseDetaile

    internal fun getBalanseDetaile(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<BalanseModel>>>{
        viewModelScope.launch(main) {
            _getBalanseDetaile.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.getBalanseDetaile(token)
                    _getBalanseDetaile.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _getBalanseDetaile.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return getBalanseDetaile
    }

    private var _getMyBrigada =
        SingleLiveEvent<ResultData<BaseResponse<ArrayList<MyBrigadaModel>>>>()

    val getMyBrigada: SingleLiveEvent<ResultData<BaseResponse<ArrayList<MyBrigadaModel>>>> =
        _getMyBrigada

    internal fun getMyBrigada(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<MyBrigadaModel>>>>{
        viewModelScope.launch(main) {
            _getMyBrigada.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.getMyBrigada(token)
                    _getMyBrigada.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _getMyBrigada.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return getMyBrigada
    }


    private var _cardDelete =
        SingleLiveEvent<ResultData<BaseResponse<String>>>()

    val cardDelete: SingleLiveEvent<ResultData<BaseResponse<String>>> =
        _cardDelete

    internal fun cardDelete(
        token: String,
        card_id:Int
    ): SingleLiveEvent<ResultData<BaseResponse<String>>>{
        viewModelScope.launch(main) {
            _cardDelete.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.cardDelete(token,card_id)
                    _cardDelete.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _cardDelete.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return cardDelete
    }

    private var _cardUpdate =
        SingleLiveEvent<ResultData<BaseResponse<addCardRequest>>>()

    val cardUpdate: SingleLiveEvent<ResultData<BaseResponse<addCardRequest>>> =
        _cardUpdate

    internal fun cardUpdate(
        token: String,
        upCard:addCardRequest,
        card_id:Int
    ): SingleLiveEvent<ResultData<BaseResponse<addCardRequest>>>{
        viewModelScope.launch(main) {
            _cardUpdate.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.cardUpdate(token,upCard,card_id)
                    _cardUpdate.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _cardUpdate.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return cardUpdate
    }

    private var _cardMoneyTaking =
        SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>>()

    val cardMoneyTaking: SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>> =
        _cardMoneyTaking

    internal fun monekTaking(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>>{
        viewModelScope.launch(main) {
            _cardMoneyTaking.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.moneyTaking(token)
                    _cardMoneyTaking.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _cardMoneyTaking.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return cardMoneyTaking
    }

    private var _createMoney =
        SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>()

    val createMoney: SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>> =
        _createMoney

    internal fun createMoney(
        token: String,
        moneyCreateRequest: moneyCreateRequest
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>{
        viewModelScope.launch(main) {
            _createMoney.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.createMoney(token,moneyCreateRequest)
                    _createMoney.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _createMoney.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return createMoney
    }


    private var _bonuseBalanse =
        SingleLiveEvent<ResultData<BaseResponse<BonusBalanse>>>()

    val bonuseBalanse: SingleLiveEvent<ResultData<BaseResponse<BonusBalanse>>> =
        _bonuseBalanse

    internal fun bonuseBalanse(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<BonusBalanse>>>{
        viewModelScope.launch(main) {
            _bonuseBalanse.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.bonusBalanse(token)
                    _bonuseBalanse.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                _bonuseBalanse.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return bonuseBalanse
    }



    private var _bonuseDriver =
        SingleLiveEvent<ResultData<BaseResponse<BonuseDriversModel>>>()

    val bonuseDriver: SingleLiveEvent<ResultData<BaseResponse<BonuseDriversModel>>> =
        _bonuseDriver

    internal fun bonuseDriver(
        token: String,
        day:String
    ): SingleLiveEvent<ResultData<BaseResponse<BonuseDriversModel>>>{
        viewModelScope.launch(main) {
            _bonuseDriver.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.bonusDriver(token,day)
                    _bonuseDriver.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                _bonuseDriver.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return bonuseDriver
    }

    private var _bonuseMoneyTaking =
        SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>>()

    val bonuseMoneyTaking: SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>> =
        _bonuseMoneyTaking

    internal fun bonuseMonekTaking(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<MoneyTaking>>>{
        viewModelScope.launch(main) {
            _bonuseMoneyTaking.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.bonuseMoneyTaking(token)
                    _bonuseMoneyTaking.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _bonuseMoneyTaking.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return bonuseMoneyTaking
    }

    private var _bonuseCreateMoney =
        SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>()

    val bonuseCreateMoney: SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>> =
        _bonuseCreateMoney

    internal fun bonuseCreateMoney(
        token: String,
        moneyCreateRequest: moneyCreateRequest
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>{
        viewModelScope.launch(main) {
            _bonuseCreateMoney.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.bonuseCreateMoney(token,moneyCreateRequest)
                    _bonuseCreateMoney.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _bonuseCreateMoney.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return bonuseCreateMoney
    }



    private var _orderHistory =
        SingleLiveEvent<ResultData<BaseResponse<OrderHistoryTravel>>>()

    val orderHistory: SingleLiveEvent<ResultData<BaseResponse<OrderHistoryTravel>>> =
        _orderHistory

    internal fun orderHistory(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<OrderHistoryTravel>>>{
        viewModelScope.launch(main) {
            _orderHistory.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.orderHistory(token)
                    _orderHistory.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _orderHistory.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return orderHistory
    }


    private var _orderBalanse =
        SingleLiveEvent<ResultData<BaseResponse<OrderHistory>>>()

    val orderBalanse: SingleLiveEvent<ResultData<BaseResponse<OrderHistory>>> =
        _orderBalanse

    internal fun orderBalanse(
        token: String,
        day: DayRequest
    ): SingleLiveEvent<ResultData<BaseResponse<OrderHistory>>>{
        viewModelScope.launch(main) {
            _orderBalanse.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.orderBalanse(token,day)
                    _orderBalanse.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                _orderBalanse.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return orderBalanse
    }

    private var _driverReferrals =
        SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>()

    val driverReferrals: SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>> =
        _driverReferrals

    internal fun driverReferrals(
        token: String,
       phone: driverReferralsRequest
    ): SingleLiveEvent<ResultData<BaseResponse<ArrayList<CreateMoneyModel>>>>{
        viewModelScope.launch(main) {
            _driverReferrals.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.driverReferrals(token,phone)
                    _driverReferrals.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                Log.e("xatolikniqara", e.toString())
                _driverReferrals.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return driverReferrals
    }

    private var _driverMe =
        SingleLiveEvent<ResultData<BaseResponse<DriverMe>>>()

    val driverMe: SingleLiveEvent<ResultData<BaseResponse<DriverMe>>> =
        _driverMe

    internal fun driverMe(
        token: String,
    ): SingleLiveEvent<ResultData<BaseResponse<DriverMe>>>{
        viewModelScope.launch(main) {
            _driverMe.postValue(ResultData.loading())
            try {
                async(context = io) {
                    delay(1L)
                    val result = repository.driverMe(token)
                    _driverMe.postValue(ResultData.success(result).data!!)
                }

            } catch (e: Throwable) {
                _driverMe.postValue(ResultData.error(e.message ?: "Unknown error"))
            }
        }

        return driverMe
    }
}