package dev.future.taxipark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dev.future.taxipark.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setOnClickListener {
            binding.drawerLayout.open()
        }

        setupNavigation()
        binding.navView.itemIconTintList = null
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(menuItems, binding.drawerLayout)
        binding.navView.setupWithNavController(navController)

//
//        HomeFragment.listener = onDrawerOpenListener

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }

    }

    private fun openDrawer() {
        listener?.onDrawerOpen()
    }

    companion object {
        var listener: NavigationOpenListener? = null
    }

}

interface NavigationOpenListener {
    fun onDrawerOpen()
}