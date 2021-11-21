package com.los3molineros.snooker.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.data.local.AppDataBase
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.ActivityMainBinding
import com.los3molineros.snooker.domain.main.MainRepositoryImpl
import com.los3molineros.snooker.presentation.MainViewModel
import com.los3molineros.snooker.presentation.MainViewModelFactory
import com.los3molineros.snooker.ui.navigation.MainNavigation


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            MainRepositoryImpl(
                RemotePlayerDataSource(RetrofitClient.webservice),
                LocalPlayerDataSource(AppDataBase.getDatabase(baseContext).playerDao())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initViews()
        initObservables()
    }

    private fun initObservables() {
        viewModel.playersLoaded.observe(this, Observer {
            if (it == true) {
                startActivity(Intent(binding.root.context, MainNavigation::class.java))
            } else {
                binding.ivIcon.visibility = View.GONE
                binding.txtLoading.visibility = View.GONE
                binding.txtDots.visibility = View.GONE
                binding.ivWorking.visibility = View.VISIBLE
                Snackbar.make(
                    binding.root, "Service not available. Try later",
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                ).show()
            }
        })
    }

    private fun initViews() {
        binding.ivIcon.visibility = View.VISIBLE
        binding.txtLoading.visibility = View.VISIBLE
        binding.txtDots.visibility = View.VISIBLE
        binding.ivWorking.visibility = View.GONE
    }

}