package com.los3molineros.snooker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.domain.main.PlayerRepository
import kotlinx.coroutines.Dispatchers

class PlayerViewModel(private val repo: PlayerRepository): ViewModel() {
    fun fetchPlayer(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getPlayer(id)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class PlayerViewModelFactory(private val repo: PlayerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PlayerRepository::class.java).newInstance(repo)
    }
}