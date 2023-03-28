package dev.future.taxipark.ui.drawer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentMoyDannixBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.model.Driver
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class MoyDannixFragment : BaseFragment<FragmentMoyDannixBinding, OrderViewModel>() {

    var driver: Driver? = null

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {

        driverMe()

        binding.edtIsm.setText(driver?.firstName)
        binding.edtFamilya.setText(driver?.lastName)
        binding.edtOchstva.setText(driver?.middleName)
        binding.edtPhone.setText(driver?.phone)
        binding.edtSerianomer.setText(driver?.vehicleLicence)
        binding.edtMarkasi.setText(driver?.vehicleBrand)
        binding.edtModel.setText(driver?.vehicleModel)
        binding.edtDavlatRaqami.setText(driver?.vehicleNumber)



    }

    private fun driverMe() {
        viewModel.driverMe(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                Log.e("TAG", "data ${status} ")
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        response?.data?.driver.let {
                            driver = it
                            binding.edtIsm.setText(it?.firstName)
                            binding.edtFamilya.setText(it?.lastName)
                            binding.edtOchstva.setText(it?.middleName)
                            binding.edtPhone.setText(it?.phone)
                            binding.edtSerianomer.setText(it?.vehicleLicence)
                            binding.edtMarkasi.setText(it?.vehicleBrand)
                            binding.edtModel.setText(it?.vehicleModel)
                            binding.edtDavlatRaqami.setText(it?.vehicleNumber)
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {

                    }
                }
            }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoyDannixBinding = FragmentMoyDannixBinding.inflate(inflater, container, false)

}