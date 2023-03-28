package dev.future.taxipark.ui.detailes.brigada

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentAddBrigadaBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.model.request.driverReferralsRequest
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.ui.registeration.model.request.registerRequest
import dev.future.taxipark.ui.registeration.ui.register.registerCode.RegisterCodeFragment
import dev.future.taxipark.ui.registeration.ui.register.registerPhone.RegisterPhoneFragment
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.gone
import dev.future.taxipark.utils.replace
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import dev.future.taxipark.utils.visible


class AddBrigadaFragment : BaseFragment<FragmentAddBrigadaBinding, OrderViewModel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {

        binding.apply {

            etPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (s?.length == 12) {
                        btnLogin.backgroundTintList =
                            AppCompatResources.getColorStateList(
                                requireContext(),
                                R.color.birigada_toolbar
                            )
                        btnLogin.isEnabled = true
                    } else {
                        btnLogin.backgroundTintList =
                            AppCompatResources.getColorStateList(
                                requireContext(),
                                R.color.btn_otpravit
                            )
                        btnLogin.isEnabled = false
                    }
                }
            })


            btnLogin.setOnClickListener {
                btnLogin.gone()
                var phone = etPhoneNumber.text.toString()
                viewModel.driverReferrals(
                    SaveUserInformation.getAuthInfo().authKey.toString(),
                    driverReferralsRequest("+998" + " " + phone)
                )
                    .observe(viewLifecycleOwner) {
                        when (it.status) {
                            Status.SUCCESS -> it.data.let { data ->
                                if (data?.success == true) {
                                    binding.etPhoneNumber
                                    findNavController().popBackStack()
                                } else {
                                    data?.error.let { errors ->
                                    }
                                }
                                progress.gone()
                                btnLogin.visible()
                            }
                            Status.LOADING -> {
                                progress.visible()
                            }
                            Status.ERROR -> it.message.let { error ->
                                btnLogin.visible()
                                snackBar(error.toString())
                                progress.gone()
                            }
                        }
                    }
            }
        }
    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBrigadaBinding = FragmentAddBrigadaBinding.inflate(inflater, container, false)


}