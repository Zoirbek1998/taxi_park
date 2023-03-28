package dev.future.taxipark.main

import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseActivity
import dev.future.taxipark.databinding.ActivityLanguageBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.drawer.viewModel.OrderViewModel
import dev.future.taxipark.ui.splash.SplashActivity
import dev.future.taxipark.utils.changeTextColor
import dev.future.taxipark.utils.changeTextSize

class LanguageActivity : BaseActivity<ActivityLanguageBinding, OrderViewModel>() {

    private fun changeLanguage() {
        binding.apply {
            lvRusskiy.setOnClickListener {
                // changeColorBackGround(lvRusskiy,lvUzbekLotin)
                changeTextColor(tvRusskiy, tvUzbekLotin)
                setLanguage("ru")
                //  updateBaseContextLocale(this@LanguageActivity)
                startActivity(Intent(this@LanguageActivity, SplashActivity::class.java))
                finish()

            }
            lvUzbekLotin.setOnClickListener {
                // changeColorBackGround(lvUzbekLotin,lvRusskiy)
                changeTextColor(tvUzbekLotin, tvRusskiy)
                setLanguage("uz")
                // updateBaseContextLocale(this@LanguageActivity)
                startActivity(Intent(this@LanguageActivity, SplashActivity::class.java))
                finish()

            }
        }
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }



    override fun getViewModelClass(): Class<OrderViewModel> = OrderViewModel::class.java

    override fun init() = with(binding) {
        window.statusBarColor = ContextCompat.getColor(this@LanguageActivity, R.color.backgraund)

        changeTextSize()
        changeLanguage()
        binding.btnLang.setOnClickListener {
            startActivity(Intent(this@LanguageActivity, SplashActivity::class.java))
            finish()
        }
    }
    fun changeTextSize()= with(binding){
        tvChooseLanguage.changeTextSize(15f)
        tvRusskiy.changeTextSize(17F)
        tvChooseRus.changeTextSize(17F)
        tvUzbekLotin.changeTextSize(15F)
    }


    override fun setupViewBinding(inflater: LayoutInflater): ActivityLanguageBinding =
        ActivityLanguageBinding.inflate(inflater)


}