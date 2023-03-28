package dev.future.taxipark.ui.splash

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.future.customdriwermenu.MenuAdaper
import dev.future.taxipark.R
import dev.future.taxipark.base.BaseActivity

import dev.future.taxipark.databinding.ActivitySplashBinding
import dev.future.taxipark.databinding.LeftDriwerMenuItemBinding
import dev.future.taxipark.di.factory.injectViewModel
import dev.future.taxipark.ui.auth.viewmodel.AuthViewmodel
import dev.future.taxipark.ui.drawer.model.MenuModel
import dev.future.taxipark.utils.RegLog
import dev.future.taxipark.utils.gone
import dev.future.taxipark.utils.sharedPref.SaveUserInformation
import dev.future.taxipark.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.WebSocket
import kotlin.coroutines.CoroutineContext

class SplashActivity : BaseActivity<ActivitySplashBinding, AuthViewmodel>(), CoroutineScope,
    MenuAdaper.OnClickListener {
    lateinit var menuList: ArrayList<MenuModel>
    lateinit var menuAdaper: MenuAdaper

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val menuItems = setOf(
        R.id.nav_balanse,
        R.id.nav_person,
        R.id.nav_card,
        R.id.nav_rekvisid,
        R.id.nav_bonuslar,
        R.id.nav_brigada,
        R.id.nav_bildirishnomalar,
        R.id.nav_kantaktlar,
        R.id.nav_chiqish,
    )




    override fun init() {
        binding.toolbar.setOnClickListener {
            binding.drawerLayout.open()
        }
        setupNavigation()
        binding.navView.itemIconTintList = null


        menuList = ArrayList()
        menuList.add(MenuModel(R.drawable.ic_balanse, getString(R.string.balans)))
        menuList.add(MenuModel(R.drawable.ic_person, getString(R.string.mening_tafsilotlarim)))
//        menuList.add(MenuModel(R.drawable.ic_card, getString(R.string.pul_o_tkazish_talablari)))
        menuList.add(MenuModel(R.drawable.ic_rekvisid, getString(R.string.rekvizitlar)))
        menuList.add(MenuModel(R.drawable.ic_bonus, getString(R.string.bonuslar)))
        menuList.add(MenuModel(R.drawable.ic_brigada, getString(R.string.mening_brigadam)))
//        menuList.add(MenuModel(R.drawable.ic_navigation, getString(R.string.bildirishnomalar)))
        menuList.add(MenuModel(R.drawable.ic_travel_history, getString(R.string.buyurtmalar_tarxi)))
        menuList.add(MenuModel(R.drawable.ic_phone_outline, getString(R.string.kantakt)))
        menuList.add(MenuModel(R.drawable.ic_chiqish, getString(R.string.chiqish)))


        menuAdaper = MenuAdaper(menuList, this)

        val viewHeader = binding.navView.getHeaderView(0)

        val navViewHeaderBinding: LeftDriwerMenuItemBinding =
            LeftDriwerMenuItemBinding.bind(viewHeader)

        navViewHeaderBinding.driverId.text = "ID ${SaveUserInformation.getAuthInfo().username}"
        navViewHeaderBinding.menuItem.adapter = menuAdaper
        navViewHeaderBinding.menuItem.setHasFixedSize(true)

    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(menuItems, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { view, destination, data ->
            when (destination.id) {
                R.id.registerCodeFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.backgraund)
                    binding.toolbarContainer.gone()
                }
                R.id.registerPhoneFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.backgraund)
                    binding.toolbarContainer.gone()
                }
                R.id.registerFilterFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                    binding.toolbarContainer.gone()
                }
                R.id.successFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)

                    binding.toolbarContainer.gone()
                }
                R.id.spashFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.backgraund)
                    binding.toolbarContainer.gone()
                }
                R.id.savePinFragment -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.backgraund)
                    binding.toolbarContainer.gone()
                }
                R.id.pinActiveFragment2 -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.backgraund)
                    binding.toolbarContainer.gone()
                }
                R.id.nav_balanse -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)
                    binding.pageName.text = getString(R.string.balans)
                    backGone()
                }
                R.id.nav_person -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)

                    binding.pageName.text = getString(R.string.mening_tafsilotlarim)
                    backGone()
                }
                R.id.nav_card -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)

                    binding.pageName.text = getString(R.string.pul_o_tkazish_talablari)
                    backGone()
                }
                R.id.nav_rekvisid -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)

                    binding.pageName.text = getString(R.string.rekvizitlar)
                    backGone()
                }
                R.id.nav_bonuslar -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.birigada_toolbar)
                    binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.birigada_toolbar
                        )
                    )
                    binding.pageName.text = getString(R.string.bonuslar)
                    binding.back.gone()
                }
                R.id.nav_brigada -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.birigada_toolbar)
                    binding.pageName.text = getString(R.string.mening_brigadam)
                    binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.birigada_toolbar
                        )
                    )
                    binding.back.gone()
                }

                R.id.nav_bildirishnomalar -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)
                    binding.pageName.text = getString(R.string.bildirishnomalar)
                    backGone()
                }

                R.id.nav_kantaktlar -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                    binding.pageName.text = getString(R.string.kantakt)
                    binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.black
                        )
                    )
                    binding.back.gone()
                }
                R.id.addCardFragment -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)

                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.backgraund
                        )
                    )
                    backVisible()
                }
                R.id.balansYechFragment -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)

                    binding.pageName.text = getString(R.string.pul_yechib_olish)
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.active_color
                        )
                    )
                    backVisible()
                }
                R.id.bonusDriversFragment -> {
                    binding.toolbarContainer.visible()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.birigada_toolbar)
                    binding.pageName.text = getString(R.string.mening_brigadam)
                    binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.birigada_toolbar
                        )
                    )
                    backVisible()
                }
                R.id.hitoryTravelFragment -> {
                    binding.toolbarContainer.visible()
                    binding.pageName.text = getString(R.string.buyurtmalar_tarxi)
                    binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
                    binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
                    window.statusBarColor = ContextCompat.getColor(this, R.color.active_color)
                    binding.toolbarContainer.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.active_color
                        )
                    )
                    binding.back.gone()
                }
                R.id.secessMoneyFragment -> {
                    binding.toolbarContainer.gone()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                }

            }
        }

    }

    override fun onItemClickListener(model: MenuModel, position: Int) {

        if (position == 0) {
            navController.navigate(R.id.nav_balanse)
            binding.drawerLayout.close()
        } else if (position == 1) {
            navController.navigate(R.id.nav_person)
            binding.drawerLayout.close()
        }
//        else if (position == 2) {
//            navController.navigate(R.id.nav_card)
//            binding.drawerLayout.close()
//        }
        else if (position == 2) {
            navController.navigate(R.id.nav_rekvisid)
            binding.drawerLayout.close()
        } else if (position == 3) {
            navController.navigate(R.id.nav_bonuslar)
            binding.drawerLayout.close()
        } else if (position == 4) {
            navController.navigate(R.id.nav_brigada)
            binding.drawerLayout.close()
        }
//        else if (position == 6) {
//            navController.navigate(R.id.nav_bildirishnomalar)
//            binding.drawerLayout.close()
//        }
        else if (position == 5) {
            navController.navigate(R.id.hitoryTravelFragment)
            binding.drawerLayout.close()
        }else if (position == 6) {
            navController.navigate(R.id.nav_kantaktlar)
            binding.drawerLayout.close()
        } else if (position ==7) {
//            navController.navigate(R.id.nav_chiqish)
            binding.drawerLayout.close()
            SaveUserInformation.delete()
            SaveUserInformation.deletePassword()
            navController.navigate(R.id.registerFilterFragment)
        }

    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }


    }

    fun backGone() {
        binding.toolbarContainer.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.backgraund
            )
        )
        binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.back.gone()
    }

    fun backVisible() {
        binding.toolbar.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.pageName.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.back.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.back.visible()
        binding.back.setOnClickListener {
            super.onBackPressed()
        }
    }

    fun progressShow() {
        binding.progress.visible()
    }

    fun progressGone() {
        binding.progress.gone()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }


    override fun getViewModelClass(): Class<AuthViewmodel> {
        return AuthViewmodel::class.java
    }


    override fun setupViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

}

