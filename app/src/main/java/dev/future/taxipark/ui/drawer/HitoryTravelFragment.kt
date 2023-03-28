package dev.future.taxipark.ui.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentHitoryTravelBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.history.HistoryTravelAdapter
import dev.future.taxipark.ui.drawer.model.OrderHistoryItem
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class HitoryTravelFragment : BaseFragment<FragmentHitoryTravelBinding,OrderViewModel>(),
    HistoryTravelAdapter.CallBackHistory {


    private val orderHistoryTravelAdapter: HistoryTravelAdapter by lazy {
        HistoryTravelAdapter(requireContext(),this)
    }
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel>  = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {
        orderHistory()
        binding.swipe.setOnRefreshListener {
            orderHistory()
        }
        binding.orderHistory.adapter = orderHistoryTravelAdapter


    }

    private fun orderHistory() {
        viewModel.orderHistory(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        response?.data?.orderHistory.let {
                            orderHistoryTravelAdapter?.items = it as List<OrderHistoryItem>
                        }
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
    ): FragmentHitoryTravelBinding  = FragmentHitoryTravelBinding.inflate(inflater,container,false)

    override fun onClick(item: OrderHistoryItem) {
        var bundle  = Bundle()
        bundle.putParcelable("history",item)
        findNavController().navigate(R.id.orderHistoryDetailesFragment,bundle)
    }


}