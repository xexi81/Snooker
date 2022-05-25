package com.los3molineros.snooker.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.common.Resource
import com.los3molineros.snooker.domain.main.TournamentRepository
import kotlinx.coroutines.Dispatchers

class TournamentViewModel(private val repo: TournamentRepository) : ViewModel() {
    private val _tournamentID = MutableLiveData<Int>()

    fun setID(id: Int) {
        _tournamentID.value = id
    }

    fun fetchTournament() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getTournament(_tournamentID.value!!)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun fetchMatchesTournament() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(repo.getMatches(_tournamentID.value!!)))
        } catch (e: IllegalStateException) {
            emit(Resource.Failure(Exception("No data yet")))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class TournamentViewModelFactory(private val repo: TournamentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TournamentRepository::class.java).newInstance(repo)
    }
}