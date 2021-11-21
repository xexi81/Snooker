package com.los3molineros.snooker.ui.player

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.data.model.MatchUI
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.ActivityPlayerBinding
import com.los3molineros.snooker.domain.main.PlayerRepositoryImpl
import com.los3molineros.snooker.presentation.PlayerViewModel
import com.los3molineros.snooker.presentation.PlayerViewModelFactory
import com.los3molineros.snooker.presentation.RankingViewModel
import com.squareup.picasso.Picasso

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private var playerId: Int = 0

    private val viewModel by viewModels<PlayerViewModel> {
        PlayerViewModelFactory(
            PlayerRepositoryImpl
            (RemotePlayerDataSource(RetrofitClient.webservice))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initBanner()
        initFormats()
        initSubscribers()
    }

    private fun initBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }

    private fun initFormats() {
        val bundle = intent.extras
        playerId = bundle?.getInt("ID") ?: 0
    }

    private fun initSubscribers() {
        viewModel.fetchPlayer(playerId).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (!it.data?.photo.isNullOrEmpty()) {
                        Picasso.get().load(it.data?.photo).into(binding.ivBackground)
                    }

                    binding.txtPlayer.text = it.data?.name
                    binding.txtBorn.text = it.data?.born
                    binding.txtFirstAsPro.text = it.data?.firstSeasonAsPro.toString()
                    binding.txtNationality.text = it.data?.nationality

                    if (it.data?.bioPage.isNullOrEmpty()) {
                        binding.biopage.visibility = View.GONE
                    }

                    it.data?.bioPage.let {  bio ->
                        binding.biopage.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bio)))
                        }
                    }

                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        })
    }

}