package dev.future.taxipark.ui.registeration.ui.PinView

import `in`.arjsna.passcodeview.PassCodeView
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dev.future.taxipark.R
import dev.future.taxipark.utils.sharedPref.SaveUserInformation


class PinActiveFragment : Fragment() {
    private val PASSCODE =SaveUserInformation.getPassword()
    private var passCodeView: PassCodeView? = null
    private var passwordBack: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mRootView = inflater.inflate(R.layout.fragment_pin_active, container, false)
        passCodeView = mRootView.findViewById<View>(R.id.pass_code_view) as PassCodeView
        passwordBack = mRootView.findViewById<View>(R.id.password_remuve) as TextView

        passCodeView?.setKeyTextColor(R.color.black)
        bindEvents()
        return mRootView
    }


    private fun bindEvents() {
        passCodeView!!.setOnTextChangeListener(object : PassCodeView.TextChangeListener {
            override fun onTextChanged(text: String) {
                if (text.length == 5) {
                    if (text == PASSCODE) {
                        findNavController().navigate(R.id.action_pinActiveFragment2_to_nav_balanse)
                    } else {
                        passCodeView!!.setError(true)
                    }
                }
            }
        })

        passwordBack!!.setOnClickListener {
            SaveUserInformation.deletePassword()
            findNavController().navigate(R.id.action_pinActiveFragment2_to_savePinFragment)
        }

    }


}