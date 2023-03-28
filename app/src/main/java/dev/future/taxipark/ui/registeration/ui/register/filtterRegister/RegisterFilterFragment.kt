package dev.future.taxipark.ui.registeration.ui.register.filtterRegister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.databinding.FragmentRegisterFilterBinding


class RegisterFilterFragment : Fragment() {
    private var _binding: FragmentRegisterFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterFilterBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.black)
//        binding.registerRegBtn.setOnClickListener {
//            findNavController().navigate(R.id.registerPhoneFragment)
//        }
        binding.regVixodBtn.setOnClickListener {
            findNavController().navigate(R.id.registerPhoneFragment)
        }
        binding.btnKantakt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFilterFragment_to_kantaktFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}