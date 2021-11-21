package com.los3molineros.snooker.ui.navigation.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource
import com.los3molineros.snooker.data.remote.RetrofitClient
import com.los3molineros.snooker.databinding.FragmentResultsBinding
import com.los3molineros.snooker.domain.main.EventRepositoryImpl
import com.los3molineros.snooker.presentation.ResultsViewModel
import com.los3molineros.snooker.presentation.ResultsViewModelFactory


class ResultsFragment : Fragment(R.layout.fragment_results) {
    private lateinit var binding: FragmentResultsBinding
    private lateinit var adapter: EventAdapter

    private val viewModel by viewModels<ResultsViewModel> {
        ResultsViewModelFactory(
            EventRepositoryImpl(
                RemoteEventsDataSource(RetrofitClient.webservice),
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentResultsBinding.bind(view)

        initUI()
    }

    private fun initUI() {
        initFormats()
        initSubscribers()
    }

    private fun initFormats() {

        adapter = EventAdapter(listOf())
        binding.rvEvents.adapter = adapter
    }


    private fun initSubscribers() {
        viewModel.fetchEvents().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapter.setEvent(it.data)
                    binding.progressBar.visibility = View.GONE
                    binding.rvEvents.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    Snackbar.make(
                        binding.root, "${it.exception.message}",
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }
    }


}