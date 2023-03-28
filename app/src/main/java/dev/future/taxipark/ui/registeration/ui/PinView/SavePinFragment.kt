package dev.future.taxipark.ui.registeration.ui.PinView

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.databinding.FragmentSavePinBinding
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class SavePinFragment : Fragment() {
    lateinit var binding : FragmentSavePinBinding

    override fun onCreateView(
        inflater: LayoutInflater,  container: ViewGroup?,
         savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            SaveUserInformation.savePassword(binding.etCode.otp.toString())
            findNavController().navigate(R.id.action_savePinFragment_to_pinActiveFragment2)
        }


    }



}