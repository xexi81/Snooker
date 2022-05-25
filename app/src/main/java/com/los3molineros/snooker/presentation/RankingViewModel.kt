package com.los3molineros.snooker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.domain.main.RankingRepository
import kotlinx.coroutines.Dispatchers

class RankingViewModel(private val repo: RankingRepository): ViewModel() {
    fun fetchRanking(type: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getRanking(type)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}


class RankingViewModelFactory(private val repo: RankingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RankingRepository::class.java).newInstance(repo)
    }
}