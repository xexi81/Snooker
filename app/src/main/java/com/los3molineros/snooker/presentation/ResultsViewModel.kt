package com.los3molineros.snooker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.domain.main.EventRepository
import kotlinx.coroutines.Dispatchers

class ResultsViewModel(private val repo: EventRepository) : ViewModel() {
        fun fetchEvents() = liveData(Dispatchers.IO) {
            emit(Resource.Loading())

            try {
                emit(Resource.Success(repo.getSeasonEvents()))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}


class ResultsViewModelFactory(private val repo: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EventRepository::class.java).newInstance(repo)
    }
}