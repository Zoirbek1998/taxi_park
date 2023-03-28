package dev.future.taxipark.ui.detailes.rekvizit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentAddCardBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.model.CardsModel
import dev.future.taxipark.ui.drawer.model.request.addCardRequest
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.utils.*
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class AddCardFragment : BaseFragment<FragmentAddCardBinding, OrderViewModel>() {
    var item: CardsModel? = null

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun onCreate() {

    }

    override fun init() = with(binding) {

         item = arguments?.getParcelable("model")

        Log.e("TAG", "inittent: $item", )

        bankName.setText(item?.bank)
        cardFio.setText(item?.fullName)
        cardNumber.setText(item?.cardNumber)

        addCard.setOnClickListener {
            if (item?.id==null) {
                addCard()
            } else {
                update()
            }
        }


    }

    private fun update() = with(binding) {

        var bankName = bankName.text.toString().trim()
        var fio = cardFio.text.toString().trim()
        var cardNumber = cardNumber.text.toString()

        if (validate()) {
            viewModel.cardUpdate(
                SaveUserInformation.getAuthInfo().authKey.toString(),
                addCardRequest(
                    SaveUserInformation.getAuthInfo().authKey.toString(),
                    bankName,
                    fio,
                    cardNumber
                ),
                item?.id ?:-1
                ).observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let {
                        if (it?.success == true) {
                            findNavController().popBackStack()
                        }
                    }
                    Status.ERROR -> status.message.let {
                        snackBar(it.toString())

                    }
                    Status.LOADING -> {
                    }
                }
            }
        } else {
            snackBar("Iltimos polyalarni toldiring")

        }


    }


    private fun addCard() = with(binding) {
        var bankName = bankName.text.toString().trim()
        var fio = cardFio.text.toString().trim()
        var cardNumber = cardNumber.text.toString()

        if (validate()) {
            addCard.background.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.active_color
                )
            )
            addCard.isClickable = true
            viewModel.addCards(
                SaveUserInformation.getAuthInfo().authKey.toString(),
                addCardRequest(
                    SaveUserInformation.getAuthInfo().authKey.toString(),
                    bankName,
                    fio,
                    cardNumber
                )
            ).observe(viewLifecycleOwner) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let {
                        if (it?.success == true) {
//                            MainActivity().progressGone()
                            findNavController().popBackStack()
                        }
                    }
                    Status.ERROR -> status.message.let {
                        snackBar(it.toString())
//                        MainActivity().progressGone()
                    }
                    Status.LOADING -> {
//                        MainActivity().progressShow()
                    }
                }
            }
        } else {
            snackBar("Iltimos polyalarni toldiring")
            addCard.background.setTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.btn_otpravit
                )
            )
            addCard.isClickable = false
        }

    }


    fun validate(): Boolean {

        val bankName =
            binding.bankName.validate(getString(R.string.eng_kamida4)) { s -> s.length in 4..30 }

        val fio =
            binding.cardFio.validate(getString(R.string.eng_kamida4)) { s -> s.length in 4..30 }

        val cardNumber =
            binding.cardNumber.validate(getString(R.string.barcha_majburiy_maydonlarni_to_ldirish_kerak)) { s -> s.length in 4..60 }

        if (!bankName) {
            binding.bankName.requestFocus()
        } else if (!fio) {
            binding.cardFio.requestFocus()
        } else if (!cardNumber) {
            binding.cardNumber.requestFocus()
        }
        return bankName && fio && cardNumber

    }


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddCardBinding = FragmentAddCardBinding.inflate(inflater, container, false)


}