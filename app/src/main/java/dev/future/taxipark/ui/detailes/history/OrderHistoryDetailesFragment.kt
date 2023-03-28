package dev.future.taxipark.ui.detailes.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentOrderHistoryDetailesBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.history.HistoryTravelAdapter
import dev.future.taxipark.ui.drawer.adapter.history.OrderHistoryAdapter
import dev.future.taxipark.ui.drawer.model.OrderHistory
import dev.future.taxipark.ui.drawer.model.OrderHistoryItem
import dev.future.taxipark.ui.drawer.model.OrderItem
import dev.future.taxipark.ui.drawer.model.request.DayRequest
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class OrderHistoryDetailesFragment :
    BaseFragment<FragmentOrderHistoryDetailesBinding, OrderViewModel>() {
    var item: OrderHistoryItem? = null
    private val orderBalanseAdapter: OrderHistoryAdapter by lazy {
        OrderHistoryAdapter(requireContext())
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {
        item = arguments?.getParcelable("history")
        getOrderHistory()
        binding.bonuseDriverRes.adapter = orderBalanseAdapter
        binding.swipe.setOnRefreshListener {
            getOrderHistory()
        }


    }

    private fun getOrderHistory() {
        viewModel.orderBalanse(
            SaveUserInformation.getAuthInfo().authKey.toString(),
            DayRequest("04.03.2023")
//            item?.day.toString()
        )
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        response?.data?.orderHistory.let {
                            orderBalanseAdapter.items = it as List<OrderItem>
                        }
                        binding.total.text = response?.data?.total.toString()
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
    ): FragmentOrderHistoryDetailesBinding =
        FragmentOrderHistoryDetailesBinding.inflate(inflater, container, false)


}