package dev.future.taxipark.ui.registeration.ui.successAndError

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentSecessMoneyBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel

class SecessMoneyFragment : BaseFragment<FragmentSecessMoneyBinding,OrderViewModel>() {



    override fun injectViewModel() {
       mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel>  = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {
       binding.backSuccess.setOnClickListener {
           findNavController().navigate(R.id.action_secessMoneyFragment_to_nav_balanse)
       }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecessMoneyBinding  = FragmentSecessMoneyBinding.inflate(inflater,container,false)

}