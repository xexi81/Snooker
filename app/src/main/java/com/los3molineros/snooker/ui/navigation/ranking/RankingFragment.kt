package com.los3molineros.snooker.ui.navigation.ranking

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.AppConstants.MONEY_RANKINGS
import com.los3molineros.snooker.common.AppConstants.ONE_YEAR_MONEY_RANKING
import com.los3molineros.snooker.common.CommonFunctions.buttonEffect
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.data.local.AppDataBase
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RemoteRankingDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.FragmentRankingBinding
import com.los3molineros.snooker.domain.main.RankingRepositoryImpl
import com.los3molineros.snooker.presentation.RankingViewModel
import com.los3molineros.snooker.presentation.RankingViewModelFactory

class RankingFragment : Fragment(R.layout.fragment_ranking) {
    private lateinit var binding: FragmentRankingBinding
    private lateinit var adapter: RankingAdapter
    private var type: String = MONEY_RANKINGS

    private val viewModel by viewModels<RankingViewModel> {
        RankingViewModelFactory(
            RankingRepositoryImpl(
                RemoteRankingDataSource(RetrofitClient.webservice),
                LocalPlayerDataSource(AppDataBase.getDatabase(requireContext()).playerDao()),
                RemotePlayerDataSource(RetrofitClient.webservice)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRankingBinding.bind(view)

        initUI()
    }

    private fun initUI() {
        initFormats()
        initSubscribers()
        initListeners()
    }

    private fun initFormats() {
        val drawable = binding.btnWorldRanking.background as GradientDrawable
        drawable.setStroke(10, Color.RED)

        val drawable2 = binding.btnYearRanking.background as GradientDrawable
        drawable2.setStroke(10, Color.WHITE)

        binding.progressBar.visibility = View.VISIBLE

        adapter = RankingAdapter(listOf())
        binding.rvRanking.adapter = adapter
    }

    private fun initListeners() {
        binding.btnWorldRanking.setOnClickListener {
            if (type == ONE_YEAR_MONEY_RANKING) {
                changeButton(binding.btnWorldRanking, true)
                changeButton(binding.btnYearRanking, false)
                type = MONEY_RANKINGS
                fetchNewRanking(MONEY_RANKINGS)
            }
        }

        binding.btnYearRanking.setOnClickListener {
            if (type == MONEY_RANKINGS) {
                changeButton(binding.btnWorldRanking, false)
                changeButton(binding.btnYearRanking, true)
                type = ONE_YEAR_MONEY_RANKING
                fetchNewRanking(ONE_YEAR_MONEY_RANKING)
            }
        }

        buttonEffect(binding.btnYearRanking)
        buttonEffect(binding.btnWorldRanking)
    }

    private fun changeButton(view: View, activate: Boolean) {
        val drawable = view.background as GradientDrawable
        drawable.setStroke(10, if (activate) Color.RED else Color.WHITE)
        binding.rvRanking.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun initSubscribers() {
        fetchNewRanking(type)
    }


    private fun fetchNewRanking(type: String) {
        viewModel.fetchRanking(type).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    adapter.setRanking(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.rvRanking.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    Snackbar.make(binding.root, "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }
    }
}