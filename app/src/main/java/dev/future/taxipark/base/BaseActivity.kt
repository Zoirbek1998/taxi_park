package dev.future.taxipark.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity

import dev.future.taxipark.R
import dev.future.taxipark.di.factory.ViewModelFactory
import dev.future.taxipark.ui.splash.SplashActivity
import dev.future.taxipark.utils.Keys
import dev.future.taxipark.utils.PrefsHelper

import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

abstract class BaseActivity<B : ViewBinding, V : ViewModel> : DaggerAppCompatActivity(),
    LifecycleObserver {
    private var isGPSEnabled = false

    companion object {
        lateinit var prefs: PrefsHelper
        const val LOCATION_PERMISSION_REQUEST = 101
    }
    private val gson: Gson = Gson()

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: B? = null
    protected lateinit var mViewModel: V

    protected val binding
    get() = _binding!!

    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()

    abstract fun getViewModelClass(): Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TaxiPark)
        super.onCreate(savedInstanceState)
        Log.e( "prefHelper:",prefs.language.toString())
        injectViewModel()
        _binding = setupViewBinding(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(this)

        init()
       // askLocationPermission()

    //    locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        //Check weather Location/GPS is ON or OFF
     /*   LocationUtil(this).turnGPSOn(object :
            LocationUtil.OnLocationOnListener {
            override fun locationStatus(isLocationOn: Boolean) {
                this@BaseActivity.isGPSEnabled = isLocationOn
            }
        })*/
      //  permissionLocation()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clearViewBinding() {
        _binding = null
        lifecycle.removeObserver(this)
    }

    abstract fun init()
    override fun onDestroy() {
        super.onDestroy()
     //   hideKeyboard()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
   /* private fun observeLocationUpdates() {
        locationViewModel.getLocationData.observe(this, Observer {
         /*   longitude.text = it.longitude.toString()
            latitude.text = it.latitude.toString()
            info.text = getString(R.string.location_successfully_received)*/
        })
    }*/


    override fun onStart() {
        super.onStart()
    }

     fun isLocationPermissionsGranted(): Boolean {
      return !(ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_PERMISSION_REQUEST) {
                isGPSEnabled = true
               // startLocationUpdates()
            }
        }
    }





    abstract fun setupViewBinding(inflater: LayoutInflater): B
    @SuppressWarnings("deprecation")
    fun setLanguage(language: String) {
        prefs.language = language
//        SplashActivity.language =language
        val dm = resources.displayMetrics
        val conf = resources.configuration
        val locale = Locale(language)
        conf.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            applicationContext.createConfigurationContext(conf) //for Android 7+
        } else {
            resources.updateConfiguration(conf, dm) //for Android 6-
        }
    }

    //#region Changing locale should not be lost when user closes the app. When user restarts the app,
    //app needs to load resources in the locale that was set by user last time.
    override fun attachBaseContext(context: Context) {
        prefs = PrefsHelper(gson, PreferenceManager.getDefaultSharedPreferences(context))
        super.attachBaseContext(updateBaseContextLocale(context))
    }
    fun updateBaseContextLocale(context: Context): Context {
        val locale = Locale(prefs.language ?: Keys.LANGUAGE_UZBEK)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale)
        }
        return updateResourcesLocaleLegacy(context, locale)
    }
    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val conf = context.resources.configuration
        conf.setLocale(locale)
        return context.createConfigurationContext(conf)
    }
    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val conf = resources.configuration
        conf.setLocale(locale)
        resources.updateConfiguration(conf, resources.displayMetrics)
        return context
    }


}