package dev.future.taxipark.ui.registeration.ui.kantakt

import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.test.core.app.ApplicationProvider
import dev.future.taxipark.R
import dev.future.taxipark.databinding.FragmentKantaktBinding


class KantaktFragment : Fragment() {
    lateinit var binding: FragmentKantaktBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKantaktBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.black)

        binding.phone.setOnClickListener {

            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+998 91 611 98 78")
            startActivity(dialIntent)

//            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:+998 91 611 98 78")
//
//            if (ContextCompat.checkSelfPermission(
//                    activity!!,
//                    CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                startActivity(callIntent)
//            } else {
//                requestPermissions(arrayOf(CALL_PHONE), 1)
//            }


        }
        binding.telegram.setOnClickListener {
            val telegram = Intent(Intent.ACTION_SEND)
            telegram.data = Uri.parse("http://telegram.me/myId")
            telegram.setPackage("org.telegram.messenger")
            requireActivity().startActivity(Intent.createChooser(telegram, "Share with"))
        }
    }


}