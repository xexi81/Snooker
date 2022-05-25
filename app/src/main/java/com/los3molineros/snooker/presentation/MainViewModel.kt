package com.los3molineros.snooker.presentation

import androidx.lifecycle.*
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.domain.main.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {
    private val _playersLoaded = MutableLiveData<Boolean>()
    val playersLoaded: LiveData<Boolean> get() = _playersLoaded


    init {
        viewModelScope.launch (Dispatchers.IO){
            try {
                _playersLoaded.postValue(repo.getPlayers())
            } catch (e: Exception) {
                debugLog(description = "exception viewModel ${e.message}")
                _playersLoaded.postValue(false)
            }
        }
    }
}



class MainViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MainRepository::class.java).newInstance(repo)
    }
}