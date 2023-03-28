package dev.future.taxipark.ui.drawer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentRekvizitlarBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.rekvizit.RekvizitCardAdapter
import dev.future.taxipark.ui.drawer.model.CardsModel
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation

class RekvizitlarFragment : BaseFragment<FragmentRekvizitlarBinding, OrderViewModel>(),
    RekvizitCardAdapter.ItemClick {


    private val rekvizitCardAdapter: RekvizitCardAdapter by lazy {
        RekvizitCardAdapter(this)
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }


    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init(): Unit = with(binding) {

        binding.balanseRec.adapter = rekvizitCardAdapter
        getCards()
        addCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("model", CardsModel())
            findNavController().navigate(R.id.addCardFragment, bundle)

        }
        swipe.setOnRefreshListener {
            getCards()
        }
    }

    private fun getCards() {
        viewModel.getCards(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->

                Log.e("TAG", "data ${status} ", )
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        response?.data?.let {
                            Log.e("TAG", "data ${it} ", )
                            rekvizitCardAdapter.items = it
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
    ): FragmentRekvizitlarBinding = FragmentRekvizitlarBinding.inflate(inflater, container, false)

    override fun deleteClick(item: CardsModel) {

        viewModel.cardDelete(SaveUserInformation.getAuthInfo().authKey.toString(), item.id ?: -1)
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        if (response?.success == true) {
                            getCards()

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

    override fun updateCard(item: CardsModel) {
        val bundle = Bundle()
        bundle.putParcelable("model", item)
        findNavController().navigate(R.id.addCardFragment, bundle)
    }


}