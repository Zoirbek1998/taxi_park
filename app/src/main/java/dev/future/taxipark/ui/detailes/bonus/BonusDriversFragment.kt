package dev.future.taxipark.ui.detailes.bonus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentBonusDriversBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.bonus.BonuseBalanseAdapter
import dev.future.taxipark.ui.drawer.adapter.bonus.BonuseDriverAdapter
import dev.future.taxipark.ui.drawer.model.BonusHistoryItem
import dev.future.taxipark.ui.drawer.model.ListItem
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation

class BonusDriversFragment : BaseFragment<FragmentBonusDriversBinding, OrderViewModel>() {
  var  day: BonusHistoryItem? =null
    private val bonusDriverAdapter: BonuseDriverAdapter by lazy {
        BonuseDriverAdapter()
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {
        day = arguments?.getParcelable("bonuseDriver")
        binding.bonuseDriverRes.adapter = bonusDriverAdapter

        bonuseDriver()

        binding.swipe.setOnRefreshListener {
            bonuseDriver()
        }
    }

    private fun bonuseDriver() {

        viewModel.bonuseDriver(SaveUserInformation.getAuthInfo().authKey.toString(),day?.day.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        response?.data?.list.let {
                            bonusDriverAdapter?.items = it as List<ListItem>
                        }

                        binding.total.text = response?.data?.total.toString()+" "+getString(R.string.so_m)


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
    ): FragmentBonusDriversBinding = FragmentBonusDriversBinding.inflate(inflater, container, false)


}