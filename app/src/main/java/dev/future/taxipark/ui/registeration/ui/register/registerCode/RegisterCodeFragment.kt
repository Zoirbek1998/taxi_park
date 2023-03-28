package dev.future.taxipark.ui.registeration.ui.register.registerCode

import `in`.arjsna.passcodeview.PassCodeView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentRegisterCodeBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.SplashFragment

import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel
import dev.future.taxipark.ui.drawer.BalansFragment.Companion.authToken

import dev.future.taxipark.ui.registeration.model.request.ConfirmCodeRequest
import dev.future.taxipark.ui.registeration.model.request.ResetRequestModel
import dev.future.taxipark.ui.registeration.ui.register.registerPhone.RegisterPhoneFragment.Companion.nomer
import dev.future.taxipark.ui.registeration.ui.register.registerPhone.RegisterPhoneFragment.Companion.register
import dev.future.taxipark.ui.registeration.ui.register.registerPhone.RegisterPhoneFragment.Companion.token
import dev.future.taxipark.ui.SplashFragment.Companion.RegisterOrLogin
import dev.future.taxipark.ui.SplashFragment.Companion.TimerMinut
import dev.future.taxipark.ui.SplashFragment.Companion.continueTimer

import dev.future.taxipark.utils.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterCodeFragment : BaseFragment<FragmentRegisterCodeBinding, AuthViewmodel>() {


    companion object {
        var nomerCheck: String = ""
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<AuthViewmodel> = AuthViewmodel::class.java

    override fun onCreate() {

    }

    override fun init() = with(binding) {
        Ftimer()
        SplashFragment.SMSCodeVisit=true
        nomerCheck = nomer
        etPhoneNumber.text = getString(R.string.sizga_yuborgan_kodni_raqamga_kiriting)+" "+"+998"+nomer
        etPhoneNumber.changeTextSize(18F)
        tvAgainSend.changeTextSize(15F)
        tvError.changeTextSize(15F)
        timerTextView.changeTextSize(15F)


//        btnRegister.backgroundTintList =
//            AppCompatResources.getColorStateList(requireContext(), R.color.active_color)



        btnNext.setOnClickListener {
            if (etCode.otp.toString().length != 4) {
                etCode.showError()
                return@setOnClickListener
            }

            when (RegisterOrLogin) {
                RegLog.Register -> {
                    confirmSMS()
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun Ftimer() = with(binding) {

        viewModel.timerStart(TimerMinut)
        timerTextView.visible()
        tvAgainSend.gone()

        viewModel.timerSaveFlow.onEach {milliSecond->
            TimerMinut =milliSecond
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.timerFlow
            .onEach { time ->
                continueTimer=true
                timerTextView.text = time
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.isEndFlow.onEach {
            continueTimer=false
            if (it) {
                timerTextView.gone()
                tvAgainSend.visible()
                tvAgainSend.setOnClickListener {
                    nomerCheck=""
                    if (RegisterOrLogin == RegLog.Register) {
                        viewModel.registration(register).observe(viewLifecycleOwner) { status ->
                            when (status.status) {
                                Status.SUCCESS -> status.data.let { data ->
                                    if (data?.success == true) {
                                        timerTextView.visible()
                                        tvAgainSend.gone()
                                        viewModel.timerStart(5L)
                                        btnNext.visible()
                                    }
                                }
                                Status.LOADING -> {
                                    btnNext.gone()
                                }
                                Status.ERROR -> {
                                    btnNext.visible()
                                }
                            }
                        }
                    } else {
                        viewModel.sendSmsNumber(ResetRequestModel(nomer))
                            .observe(viewLifecycleOwner) { status ->
                                when (status.status) {
                                    Status.SUCCESS -> status.data.let { data ->
                                        timerTextView.visible()
                                        tvAgainSend.gone()
                                        viewModel.timerStart(5*60000L)
                                        btnNext.visible()
                                        if (data?.success == true) {

                                        }
                                    }
                                    Status.LOADING -> {
                                        btnNext.gone()
                                    }
                                    Status.ERROR -> {
                                        btnNext.visible()
                                    }
                                }
                            }
                    }
                }
            } else {
                timerTextView.visible()
                tvAgainSend.gone()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun confirmSMS() = with(binding) {



        viewModel.confirmSms(ConfirmCodeRequest(etCode.otp.toString().toInt(), token))
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let {
                        Log.e("confirmSMS: ", it?.data.toString())
                        token = it?.data?.authKey.toString()
                        authToken = it?.data?.authKey.toString()
                        when (RegisterOrLogin) {
                            RegLog.Register -> {
                                findNavController().navigate(R.id.savePinFragment)
                            }
                        }
                        btnNext.visible()
                    }
                    Status.ERROR -> status.message.let {
                        Toast.makeText(requireContext(), it.toString() + "Hato kiritildi", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.errorFragment)

                        etCode.showError()
                        btnNext.visible()
                    }
                    Status.LOADING -> {
                        btnNext.gone()
                    }
                }
            }
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterCodeBinding  = FragmentRegisterCodeBinding.inflate(inflater, container, false)

}