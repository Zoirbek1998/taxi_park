package dev.future.taxipark.ui.detailes.bonus

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.*
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.balanse.MoneyTakingAdapter
import dev.future.taxipark.ui.drawer.adapter.rekvizit.RekvizitCardAdapter
import dev.future.taxipark.ui.drawer.model.CardsItem
import dev.future.taxipark.ui.drawer.model.Limits
import dev.future.taxipark.ui.drawer.model.TransactionsItem
import dev.future.taxipark.ui.drawer.model.request.moneyCreateRequest
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation

class BonusYechFragment : BaseFragment<FragmentBonusYechBinding, OrderViewModel>(),MoneyTakingAdapter.MoneyCallBack {

    var limit: Limits? = null
    var cardItem: CardsItem? = null
    private var _alertDialogBinding: LimitCommisionDialogBinding? = null
    private val alertDialogBinding get() = _alertDialogBinding!!

    private val alertDialog by lazy {
        _alertDialogBinding = LimitCommisionDialogBinding.inflate(layoutInflater)
        context?.let {
            AlertDialog.Builder(it)
                .setView(alertDialogBinding.root)
                .create()
        }
    }

    private val moneyTakingAdapter: MoneyTakingAdapter by lazy {
        MoneyTakingAdapter(this)
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() = with(binding) {

        recCards.adapter = moneyTakingAdapter
        moneyTaking()
        initView()

        swipe.setOnRefreshListener {
            moneyTaking()
        }




    }

    private fun initView() = with(binding) {

        pageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        moneyCreate.setOnClickListener {
            if (cardItem?.id == null){
                snackBar("Iltimos cartani tanlang!")
            }else{
                var summa = binding.balanseSumma.text.toString().toInt()
                if (summa< limit?.minBalance!!){
                    snackBar("Iltimos ${limit?.minBalance!!} so'mdan baland summa kiriting")
                }else if(summa > limit?.maxLimit!!) {
                    moneyCreatView()
                }else{
                    snackBar("Iltimos ${limit?.maxLimit!!} so'mdan baland summa kiriting")
                }

            }

        }

        imgPrasent.setOnClickListener {
            showDialog(limit!!)
        }

        balanseSumma.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                Log.e("TEXT", " ${s}")

//                if (s.toString().toInt() <= limit?.minBalance!!) {
//                    comisya.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellov))
//                    comisya.setText(getString(R.string.ariza_miqdori_minimaldan_kam) + " " + "(" + limit?.minBalance + ")")
//                } else if (s.toString().toInt()==" ".toInt()){
//                    comisya.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//                    comisya.setText(getString(R.string.mablag_larni_olib_qo_yish_bo_yicha_komissiya_0) + " " + "${limit?.minComission}")
//                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })


    }

    private fun moneyCreatView() {
        var summa = binding.balanseSumma.text.toString()
        viewModel.bonuseCreateMoney(SaveUserInformation.getAuthInfo().authKey.toString(),
            moneyCreateRequest(cardItem?.id!!,summa)
        ).observe(viewLifecycleOwner){status ->

            when(status.status){
                Status.SUCCESS -> status.data.let { response ->
                    binding.swipe.isRefreshing = false
                    findNavController().navigate(R.id.secessMoneyFragment)
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

    private fun moneyTaking() = with(binding) {
        viewModel.bonuseMonekTaking(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false
                        balanse.text =
                            getString(R.string.balans) + " " + response?.data?.balance + " " + getString(
                                R.string.so_m
                            )

                        limit = response?.data?.limits

                        response?.data?.cards.let {
                            moneyTakingAdapter.items = it as List<CardsItem>
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


    private fun showDialog(model: Limits) {
        if (alertDialog?.window != null) {
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        with(alertDialogBinding) {
            dailyLimit.text  = model.dailyLimit.toString().trim()+getString(R.string.so_m)
            maxLimit.text  = model.maxLimit.toString().trim()+getString(R.string.so_m)
            minLimit.text  = model.minLimit.toString().trim()+getString(R.string.so_m)
            minBalance.text  = model.minBalance.toString().trim()+getString(R.string.so_m)
            comissionPrasent.text  = model.comission.toString().trim()+"%"
            minComission.text  = model.minComission.toString().trim()+getString(R.string.so_m)
        }


        alertDialogBinding.btnBack.setOnClickListener {
            alertDialog?.dismiss()
        }



        alertDialog?.show()

    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBonusYechBinding = FragmentBonusYechBinding.inflate(inflater, container, false)

    override fun onClikItem(item: CardsItem) {
        cardItem = item
    }

}


