package com.los3molineros.snooker.ui.navigation.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.CommonFunctions.returnShortDate
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.data.local.AppDataBase
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.FragmentTournamentDetailBinding
import com.los3molineros.snooker.domain.main.TournamentRepositoryImpl
import com.los3molineros.snooker.presentation.TournamentViewModel
import com.los3molineros.snooker.presentation.TournamentViewModelFactory
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat


class TournamentDetailFragment : Fragment(R.layout.fragment_tournament_detail) {
    private lateinit var binding: FragmentTournamentDetailBinding
    val formatter: NumberFormat = DecimalFormat("#,###")

    private val viewModel: TournamentViewModel by activityViewModels {
        TournamentViewModelFactory(
            TournamentRepositoryImpl(
                RemoteEventsDataSource(RetrofitClient.webservice),
                RemotePlayerDataSource(RetrofitClient.webservice),
                LocalPlayerDataSource(AppDataBase.getDatabase(requireContext()).playerDao())
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTournamentDetailBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        initSubscribers()
    }

    private fun initSubscribers() {
        viewModel.fetchTournament().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtFinishDate.text = returnShortDate(it.data.FinishDate)
                    binding.txtStartDate.text = returnShortDate(it.data.StartDate)
                    binding.txtFormat.text = it.data.Type
                    binding.txtName.text = it.data.Name
                    binding.txtLocation.text = it.data.City
                    binding.txtVenue.text = it.data.Venue

                    if (it.data.lastChampionImage.isNotEmpty()) {
                        Picasso.get().load(it.data.lastChampionImage).into(binding.ivBackground)
                    }

                    if (it.data.winnerPrizeMoney == 0) {
                        binding.txtWinnerPrizeMoney.visibility = View.INVISIBLE
                        binding.ivWinnerPrice.visibility = View.INVISIBLE
                    } else {
                        binding.txtWinnerPrizeMoney.text =
                            formatter.format(it.data.winnerPrizeMoney)
                    }

                    if (it.data.totalPrizeMoney == 0) {
                        binding.txtTotalPrizeMoney.visibility = View.INVISIBLE
                        binding.ivTotalPrice.visibility = View.INVISIBLE
                    } else {
                        binding.txtTotalPrizeMoney.text =
                            formatter.format(it.data.totalPrizeMoney)
                    }

                    if (it.data.LastChampionName.isEmpty()) {
                        binding.txtCurrentChampion.visibility = View.INVISIBLE
                        binding.ivCurrentChampion.visibility = View.INVISIBLE
                    } else {
                        binding.txtCurrentChampion.text = it.data.LastChampionName
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(
                        binding.root, "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }
    }

}