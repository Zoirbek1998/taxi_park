package dev.future.taxipark.ui.drawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentMyBrigadeBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.myBrigada.MyBrigadaAdapter
import dev.future.taxipark.ui.drawer.adapter.rekvizit.RekvizitCardAdapter
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class MyBrigadeFragment : BaseFragment<FragmentMyBrigadeBinding, OrderViewModel>() {

    private var myBrigadaAdapter: MyBrigadaAdapter? = null
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.addBrigadaFragment)
        }

        myBrigada()
        binding.swipe.setOnRefreshListener {
            myBrigada()
        }
    }

    private fun myBrigada() {
        viewModel.getMyBrigada(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        myBrigadaAdapter = response?.data?.let {
                            MyBrigadaAdapter(it)
                        }
                        binding.myBrigadaRec.adapter = myBrigadaAdapter

                    }
                    Status.ERROR -> {
                        binding.swipe.isRefreshing = false
                    }
                    Status.LOADING -> {
                        binding.swipe.isRefreshing = true
                    }
                }
            }
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBrigadeBinding = FragmentMyBrigadeBinding.inflate(inflater, container, false)


}