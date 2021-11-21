package com.los3molineros.snooker.ui.navigation.tournament

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.data.local.AppDataBase
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.FragmentTournamentMatchesBinding
import com.los3molineros.snooker.domain.main.TournamentRepositoryImpl
import com.los3molineros.snooker.presentation.TournamentViewModel
import com.los3molineros.snooker.presentation.TournamentViewModelFactory

class TournamentMatchesFragment : Fragment(R.layout.fragment_tournament_matches) {
    lateinit var binding: FragmentTournamentMatchesBinding
    private lateinit var adapter: MatchesAdapter

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

        binding = FragmentTournamentMatchesBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        initFormaters()
        initSubscribers()
    }

    private fun initFormaters() {
        binding.progressBar.visibility = View.VISIBLE

        adapter = MatchesAdapter(listOf())
        binding.rvMatches.adapter = adapter
    }

    private fun initSubscribers() {
        viewModel.fetchMatchesTournament().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.setMatches(it.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }
    }

}