package com.los3molineros.snooker.ui.navigation.tournament

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.los3molineros.snooker.R
import com.los3molineros.snooker.data.local.AppDataBase
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.ActivityTournamentBinding
import com.los3molineros.snooker.domain.main.TournamentRepositoryImpl
import com.los3molineros.snooker.presentation.TournamentViewModel
import com.los3molineros.snooker.presentation.TournamentViewModelFactory

class TournamentActivity : AppCompatActivity() {
    lateinit var binding: ActivityTournamentBinding
    private var id: Int = 0

    private val viewModel by viewModels<TournamentViewModel> {
        TournamentViewModelFactory(
            TournamentRepositoryImpl(
                RemoteEventsDataSource(RetrofitClient.webservice),
                RemotePlayerDataSource(RetrofitClient.webservice),
                LocalPlayerDataSource(AppDataBase.getDatabase(baseContext).playerDao())
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun initUI() {
        initBundle()
        initObserver()
        initBanner()
        initBottomNav()
    }

    private fun initBundle() {
        val bundle = intent.extras
        id = bundle?.getInt("ID") ?: 0
    }

    private fun initObserver() {
        viewModel.setID(id)
    }

    private fun initBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun initBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}