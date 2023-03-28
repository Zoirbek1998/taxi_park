package dev.future.taxipark.ui.registeration.ui.successAndError

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.databinding.FragmentSuccessBinding
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class SuccessFragment : Fragment() {

    lateinit var binding : FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding = FragmentSuccessBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backNext.setOnClickListener {
            if (SaveUserInformation.getPassword().isEmpty()) {


            } else {
                findNavController().navigate(R.id.action_successFragment_to_savePinFragment)
            }
        }
    }

}