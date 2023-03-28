package dev.future.taxipark.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseActivity
import dev.future.taxipark.base.BaseFragment
import dev.future.taxipark.databinding.FragmentSplashBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.main.LanguageActivity
import dev.future.taxipark.network.ApiService
import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel
import dev.future.taxipark.ui.splash.viewpager.ViewPagerAdapter
import dev.future.taxipark.ui.splash.viewpager.ZoomOutPageTransformer
import dev.future.taxipark.utils.RegLog
import dev.future.taxipark.utils.Status
import dev.future.taxipark.utils.gone
import dev.future.taxipark.utils.sharedPref.SaveTarif
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import dev.future.taxipark.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SplashFragment : BaseFragment<FragmentSplashBinding, AuthViewmodel>(), CoroutineScope {

    private val SPLASH_DISPLAY_LENGTH = 10L

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(requireActivity()) }

    var firstVisit: Boolean = false
    var firstAudio: Boolean = false

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<AuthViewmodel>  = AuthViewmodel::class.java

    override fun onCreate() {

    }

    override fun init() {
        firstVisit = SaveTarif.getVisit()
        firstAudio = SaveTarif.getAudio()
        setupView()
    }



    private fun setupView() = with(binding) {
        AUDIO_URL = ""

        if (firstAudio) {
            viewModel.getAudio().observe(requireActivity()) { status ->
                when (status.status) {
                    Status.SUCCESS -> status.data.let { datas ->
                        if (!datas?.data?.url.isNullOrBlank()) {
                            AUDIO_URL = ApiService.IMAGE_URL + datas?.data?.url
                            Log.e("setupView:", datas?.data.toString())

                        }
                        Log.e("setupView:", datas?.data.toString())
                    }
                }
            }
        }

        Handler(requireActivity().mainLooper).postDelayed({
            if (firstVisit) {
                intent()
            } else {
                if (BaseActivity.prefs.language?.isEmpty() == true) {
                    startActivity(Intent(requireActivity(), LanguageActivity::class.java))
                } else {
                    binding.apply {
                        imageView.gone()
                        dotsIndicator.visible()
                        viewPager.visible()
                        btnNext.visible()

                    }
                    SaveTarif.saveVisit(true)
                    SaveTarif.setListenAudio(false)
//getImage
                    viewModel.getSlider()
                        .observe(requireActivity()) { status ->
                            when (status.status) {
                                Status.LOADING -> {}

                                Status.SUCCESS -> status.data.let { datas ->
                                    if (datas?.success == true) {
                                        datas.data.let {
                                            if (it?.size!! > 0) {
                                                val adapter = ViewPagerAdapter(
                                                    requireActivity(),
                                                    list = it
                                                )
                                                viewPager.adapter = adapter
                                                val zoomOutPageTransformer =
                                                    ZoomOutPageTransformer()

                                                btnNext.setOnClickListener { view ->
                                                    if (viewPager.currentItem == (it.size - 1)) {
                                                        intent()
                                                    } else {
                                                        viewPager.currentItem =
                                                            (viewPager.currentItem + 1)
                                                    }
                                                }
                                                viewPager.setPageTransformer { page, position ->
                                                    zoomOutPageTransformer.transformPage(
                                                        page,
                                                        position
                                                    )
                                                    var item = viewPager.currentItem


                                                    if (item == (it.size - 1)) btnNext.text =
                                                        getString(R.string.boshlash) else btnNext.text =
                                                        getString(R.string.keyingi)
                                                }
                                                dotsIndicator.setViewPager2(viewPager)

                                            } else {
                                                intent()
                                            }
                                        }
                                    }

                                }
                                Status.ERROR -> {

                                }
                            }
                        }
//getAudio


                }
            }
        }, SPLASH_DISPLAY_LENGTH)

    }


    companion object {

        var RegisterOrLogin: RegLog = RegLog.Login

        var continueTimer=false
        var TimerMinut:Long=(5*60000L)
        var SMSCodeVisit =false

        private const val APP_UPDATE_REQUEST_CODE = 1991
        var AUDIO_URL = ""
    }

    fun intent() {
        if (BaseActivity.prefs.language?.isEmpty() == true) {
            startActivity(Intent(requireActivity(), LanguageActivity::class.java))
            requireActivity().finish()
        } else {
            viewModel.funcgetMe(SaveUserInformation.getAuthInfo().authKey.toString())
            viewModel.getMe.observe(this) { result ->
                when (result.status) {
                    Status.SUCCESS -> result.data.let { response ->
                        Log.e("intentRegister:", response.toString())
                        SaveUserInformation.saveAuthInfo(response?.data!!)
                        if (response.data?.status == 7) {
                            findNavController().navigate(R.id.action_spashFragment_to_registerFilterFragment)
                            snackBar("Ma'lumotlar to'liq emas Qaytadan Login qiling")

                        }else if (response.data?.status == 8) {
                            findNavController().navigate(R.id.action_spashFragment_to_registerFilterFragment)
                            snackBar("Iltimos Faollashtirishni so'rang!")
                        }else if (response.data?.status == 10) {
                            findNavController().navigate(R.id.action_spashFragment_to_pinActiveFragment2)
                        }
                        else {
                            if (SaveUserInformation.getAuthInfo().authKey?.isEmpty()!!) {
                                findNavController().navigate(R.id.action_spashFragment_to_registerFilterFragment)

                            } else {
                                findNavController().navigate(R.id.action_spashFragment_to_successFragment)
                            }
                        }

                    }
                    Status.LOADING -> {
                    }
                    Status.ERROR -> result.message.let {
                        if (it.equals("401")) {
                            findNavController().navigate(R.id.action_spashFragment_to_registerFilterFragment)
                        }
                        Log.e("profildaxato", it.toString())
                    }
                }
            }


        }
    }

//    override fun onResume() {
//        super.onResume()
//        appUpdateManager
//            .appUpdateInfo
//            .addOnSuccessListener { appUpdateInfo ->
//                // If the update is downloaded but not installed,
//                // notify the user to complete the update.
//                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                    popupSnackbarForCompleteUpdate()
//                }
//
//                //Check if Immediate update is required
//                try {
//                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                        // If an in-app update is already running, resume the update.
//                        appUpdateManager.startUpdateFlowForResult(
//                            appUpdateInfo,
//                            AppUpdateType.IMMEDIATE,
//                            this,
//                            APP_UPDATE_REQUEST_CODE
//                        )
//                    }
//                } catch (e: IntentSender.SendIntentException) {
//                    e.printStackTrace()
//                }
//            }
//    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater,container,false)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}