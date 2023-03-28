package dev.future.taxipark.ui.registeration.ui.register.registerPhone

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentRegisterPhoneBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.SplashFragment

import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel

import dev.future.taxipark.ui.registeration.model.request.registerRequest
import dev.future.taxipark.ui.registeration.ui.register.registerCode.RegisterCodeFragment
import dev.future.taxipark.ui.SplashFragment.Companion.RegisterOrLogin
import dev.future.taxipark.ui.SplashFragment.Companion.TimerMinut
import dev.future.taxipark.utils.*


class RegisterPhoneFragment : BaseFragment<FragmentRegisterPhoneBinding, AuthViewmodel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<AuthViewmodel> = AuthViewmodel::class.java

    override fun onCreate() {

    }

    override fun init() {

        registration()
        RegisterOrLogin = RegLog.Register
        if (SplashFragment.continueTimer){
            viewModel.timerStart(TimerMinut)
        }

    }


    private fun registration() {
        binding.apply {

            etPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (s?.length == 12){
                    btnRegister.backgroundTintList =
                        AppCompatResources.getColorStateList(requireContext(), R.color.active_color)
                        btnRegister.isEnabled = true
                    }else{
                        btnRegister.backgroundTintList =
                            AppCompatResources.getColorStateList(requireContext(), R.color.btn_otpravit)
                        btnRegister.isEnabled = false
                    }
                }
            })


            btnRegister.setOnClickListener {
                btnRegister.gone()

                nomer = "+998 "+binding.etPhoneNumber.replace()



                val requestRegister = registerRequest(
                    phone = "+998 "+etPhoneNumber.replace().toString(),
                )
                Log.d("nomerkeldi", nomer)

                if (!RegisterCodeFragment.nomerCheck.equals(nomer)) {
                    viewModel.registration(requestRegister)
                        .observe(viewLifecycleOwner) {
                            when (it.status) {
                                Status.SUCCESS -> it.data.let { data ->
                                    if (data?.success == true) {
                                        Log.e("togri",data.data?.authKey.toString())
                                        token = data.data?.authKey.toString()
                                        nomer = binding.etPhoneNumber.replace()
                                        register = requestRegister
                                        findNavController().navigate(R.id.registerCodeFragment)

                                    } else {
                                        data?.error.let { errors ->
                                            snackBar(R.string.royxatdan_otgan.toString())
                                        }
                                    }
                                    progress.gone()
                                    btnRegister.visible()
                                }
                                Status.LOADING -> {
                                    progress.visible()
                                }
                                Status.ERROR -> it.message.let { error ->
                                    snackBar(error.toString())
                                    Log.e("registration: ", error.toString())
                                    btnRegister.visible()
                                    progress.gone()
                                }
                            }
                        }
                } else {
                    findNavController().navigate(R.id.registerCodeFragment)

                }
            }
        }
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPhoneBinding  = FragmentRegisterPhoneBinding.inflate(inflater, container, false)


    companion object {
        var token: String = ""
        var nomer: String = ""
        var register: registerRequest = registerRequest()

    }
}