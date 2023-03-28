package dev.future.taxipark.ui.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentBonusesBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.bonus.BonuseBalanseAdapter
import dev.future.taxipark.ui.drawer.model.BonusHistoryItem
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class BonusesFragment : BaseFragment<FragmentBonusesBinding, OrderViewModel>(),
    BonuseBalanseAdapter.OnClickListener {


    private val bonuseBalanseAdapter: BonuseBalanseAdapter by lazy {
        BonuseBalanseAdapter(this)
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {
        binding.balanseRec.adapter = bonuseBalanseAdapter
        bonusBalanse()

        binding.swipe.setOnRefreshListener {
            bonusBalanse()
        }

        binding.btnBonuseYech.setOnClickListener{
            findNavController().navigate(R.id.bonusYechFragment2)
        }

    }

    private fun bonusBalanse() {
        viewModel.bonuseBalanse(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        response?.data?.bonusHistory.let {
                            bonuseBalanseAdapter?.items = it as List<BonusHistoryItem>
                        }

                        binding.balanseSumma.text =
                            response?.data?.bonusBalans.toString() + " " + getString(R.string.so_m)


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
    ): FragmentBonusesBinding = FragmentBonusesBinding.inflate(inflater, container, false)

    override fun onItemClickListener(model: BonusHistoryItem) {
        var bundle = Bundle()
        bundle.putParcelable("bonuseDriver",model)
        findNavController().navigate(R.id.bonusDriversFragment,bundle)
    }


}