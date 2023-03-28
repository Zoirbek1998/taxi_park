package dev.future.taxipark.ui.drawer

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.BalanseDetaileDialogBinding
import dev.future.taxipark.databinding.FragmentBalansBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.adapter.balanse.BalanseAdapter
import dev.future.taxipark.ui.drawer.model.*
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import java.text.SimpleDateFormat
import java.util.*


class BalansFragment : BaseFragment<FragmentBalansBinding, OrderViewModel>(),
    BalanseAdapter.OnClickListener {

    private var balnseAdapter: BalanseAdapter? = null
    private var _alertDialogBinding: BalanseDetaileDialogBinding? = null
    private val alertDialogBinding get() = _alertDialogBinding!!

    private val alertDialog by lazy {
        _alertDialogBinding = BalanseDetaileDialogBinding.inflate(layoutInflater)
        context?.let {
            AlertDialog.Builder(it)
                .setView(alertDialogBinding.root)
                .create()
        }
    }

    companion object {
        var authToken: String = SaveUserInformation.getAuthInfo().authKey.toString()
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() {


        binding.balanseHistory.setOnClickListener {
            findNavController().navigate(R.id.hitoryTravelFragment)
        }

        getBalanse()

        binding.swipe.setOnRefreshListener {
            getBalanse()
        }

    }

    private fun getBalanse() {
        viewModel.getBalanseDetaile(SaveUserInformation.getAuthInfo().authKey.toString())
            .observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { response ->
                        binding.swipe.isRefreshing = false

                        if (response?.data?.transactions!!.isEmpty()) {
                            binding.balanseSumma.text = "0.0 сум"
                            binding.btnNext.setBackgroundTintList(
                                ContextCompat.getColorStateList(
                                    requireActivity(),
                                    R.color.btn_otpravit
                                )
                            )
                            binding.btnNext.setEnabled(false)
                            binding.btnNext.setOnClickListener {
                                findNavController().navigate(R.id.nav_rekvisid)
                            }
                        } else {
                            binding.btnNext.setBackgroundTintList(
                                ContextCompat.getColorStateList(
                                    requireActivity(),
                                    R.color.active_color
                                )
                            )
                            binding.balanseSumma.text =
                                response?.data?.balans.toString()+" " + getString(
                                    R.string.so_m
                                )
                            binding.btnNext.setEnabled(true)
                            binding.btnNext.setOnClickListener {
                                findNavController().navigate(R.id.balansYechFragment)
                            }
                        }


                        balnseAdapter = response?.data?.transactions.let {
                            BalanseAdapter(it as List<TransactionsItem>, this)
                        }
                        binding.balanseRec.adapter = balnseAdapter


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

    private fun showDialog(model: TransactionsItem) {
        if (alertDialog?.window != null) {
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        with(alertDialogBinding) {
            var date:Date = SimpleDateFormat("dd.MM.yyyy HH:mm").parse(model.createdAt.toString().toString())
            var dateStr:String = SimpleDateFormat("dd.MM.yyyy").format(date)
            var time:String = SimpleDateFormat("HH:mm").format(date)

            balanseSum.text = model.summa.toString() + " " + getString(R.string.so_m)
            balanseDate.text = dateStr
            balanseTime.text = time

            if (model?.status?.jsonMemberInt == PROCESS){

                balanseSum.setTextColor(ContextCompat.getColor(requireActivity(),R.color.yellov))
                balanseStatus.setTextColor(ContextCompat.getColor(requireActivity(),R.color.yellov))
                balanseComment.setTextColor(ContextCompat.getColor(requireActivity(),R.color.yellov))
                balanseStatus.text  = "В ожидании"
                balanseComment.text = "ожидании"

            }else if (model?.status?.jsonMemberInt == CONFIRM){

                balanseSum.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
                balanseStatus.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
                balanseComment.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
                balanseStatus.text  = "Проведён"
                balanseComment.text = "Заявка на вывод средств"


            }else if (model?.status?.jsonMemberInt == CANCEL){
                balanseSum.setTextColor(ContextCompat.getColor(requireActivity(),R.color.red))
                balanseStatus.setTextColor(ContextCompat.getColor(requireActivity(),R.color.red))
                balanseComment.setTextColor(ContextCompat.getColor(requireActivity(),R.color.red))
                balanseStatus.text  = "Отказано"
                balanseComment.text = "Нехватка баланса"
            }
        }


        alertDialogBinding.btnBack.setOnClickListener {
            alertDialog?.dismiss()
        }



        alertDialog?.show()

    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBalansBinding = FragmentBalansBinding.inflate(inflater, container, false)

    override fun onItemClickListener(model: TransactionsItem) {
        showDialog(model)
    }
}