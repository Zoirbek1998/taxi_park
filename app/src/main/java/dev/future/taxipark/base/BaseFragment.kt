package dev.future.taxipark.base
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.android.support.DaggerFragment

import dev.future.taxipark.di.factory.ViewModelFactory
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.splash.SplashActivity
import dev.future.taxipark.utils.Keys
import dev.future.taxipark.utils.PrefsHelper
import dev.future.taxipark.utils.viewmodel.SharedViewModel
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<B : ViewBinding, V : ViewModel> : DaggerFragment(),

    LifecycleObserver {

//    companion object {
//        lateinit var prefs: PrefsHelper
//
//    }

    private val gson: Gson = Gson()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected lateinit var mViewModel: V

    val viewModel: V get() = mViewModel

    open lateinit var sharedViewModel: SharedViewModel

    abstract fun injectViewModel()

    abstract fun getViewModelClass(): Class<V>

    private var _binding: B? = null
    protected val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
       // setStyle(STYLE_NORMAL, R.style.AppTheme)
        super.onCreate(savedInstanceState)
        sharedViewModel =injectViewModel(viewModelFactory)
      // mViewModel =injectViewModel(viewModelFactory)
        onCreate()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(this)
        init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
          injectViewModel()
        _binding = setupViewBinding(inflater, container)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    abstract fun onCreate()
    abstract fun init()
    abstract fun setupViewBinding(
        inflater: LayoutInflater, container: ViewGroup?,
    ): B

    fun setTitle(title: String?) {
        sharedViewModel.setPageTitle(title)
    }
    protected fun snackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun clearViewBinding() {
        _binding = null
        activity?.viewModelStore?.clear()
        viewLifecycleOwner.lifecycle.removeObserver(this)
    }



     fun onSupportNavigateUp(): Boolean {
        requireActivity().onBackPressed()
        return true
    }

    fun hideKeyboard() {
        val view =  requireActivity().currentFocus
        if (view != null) {
            val imm =  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}