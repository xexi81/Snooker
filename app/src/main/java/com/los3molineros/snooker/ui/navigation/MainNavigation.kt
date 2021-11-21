package com.los3molineros.snooker.ui.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.los3molineros.snooker.R
import com.los3molineros.snooker.databinding.ActivityMainNavigationBinding

class MainNavigation : AppCompatActivity() {
    lateinit var binding: ActivityMainNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun initUI() {
        initBanner()
        initBottomNav()
    }

    private fun initBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun initBottomNav() {
        val navHostFragment =  supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}