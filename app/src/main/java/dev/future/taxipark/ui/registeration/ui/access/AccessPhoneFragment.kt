package dev.future.taxipark.ui.registeration.ui.access

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentAccessPhoneBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.SplashFragment
import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel
import dev.future.taxipark.ui.drawer.BalansFragment
import dev.future.taxipark.ui.registeration.model.request.LoginRequest
import dev.future.taxipark.ui.registeration.ui.register.registerCode.RegisterCodeFragment
import dev.future.taxipark.utils.*


class AccessPhoneFragment : BaseFragment<FragmentAccessPhoneBinding, AuthViewmodel>(){

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<AuthViewmodel> = AuthViewmodel::class.java

    override fun onCreate() {
    }

    override fun init() {
        SplashFragment.TimerMinut = (5 * 60000L)
        RegisterCodeFragment.nomerCheck = ""
        SplashFragment.RegisterOrLogin = RegLog.Login
        getLogin()
        intentForgotPassword()
    }

    private fun intentForgotPassword() {
//        binding.tvForgotPassword.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_passwordResetFragment)
//        }
    }

    private fun getLogin() {
        binding.apply {
            Log.e("keldi", "getLogin: ${etPhoneNumber.text?.length}", )
            if (etPhoneNumber.text?.length == 12) {
                binding.btnLogin.getBackground().setTint(resources.getColor(R.color.active_color))

                binding.btnLogin.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.birigada_toolbar
                    )
                )

            }
            btnLogin.setOnClickListener {
                if (etPhoneNumber.replace().length != 12) {

                    snackBar(R.string.raqam_toliq_emas.toString())
                } else {
                    btnLogin.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.active_color)));
                    btnLogin.backgroundTintList =
                        AppCompatResources.getColorStateList(requireContext(), R.color.active_color)
                    viewModel.getlogin(
                        LoginRequest(phone = etPhoneNumber.replace())
                    ).observe(viewLifecycleOwner) { result ->
                        when (result.status) {
                            Status.SUCCESS -> result.data.let { data ->
                                BalansFragment.authToken = data?.data?.authKey.toString()
                                if (data?.success == true) {
                                    data.data.let { it ->
                                        if (it?.status == 7) {
                                            findNavController().navigate(R.id.registerFullFragment)
                                        } else {
//                                            startActivity(
//                                                Intent(
//                                                    requireContext(),
//                                                    MainActivity::class.java
//                                                )
//                                            )
                                            activity?.finish()
                                        }

                                    }

                                } else {
                                    data?.error.let {
                                        snackBar(it?.password?.getErrorText().toString())
                                        //  keyboard()
                                    }
                                }
                                progress.gone()
                                btnLogin.visible()
                            }
                            Status.LOADING -> {
                                btnLogin.gone()
                                progress.visible()

                            }
                            Status.ERROR -> result.message.let { error ->
                                snackBar(getString(R.string.login_parol))
                                btnLogin.visible()
                                progress.gone()
                            }
                        }

                    }
                }
            }
        }
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccessPhoneBinding =
        FragmentAccessPhoneBinding.inflate(inflater, container, false)

}