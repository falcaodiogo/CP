package ua.diogo.cp.data.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.diogo.cp.data.retrofit.entity.Stations
import ua.diogo.cp.data.retrofit.repository.StationsRepository

class StationsViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val repository = StationsRepository()
    private val _stations = MutableLiveData<List<Stations>>()
    val stations : LiveData<List<Stations>> = _stations
    fun fetchStations() {
        viewModelScope.launch {
            try {
                val stations = repository.getStations()
                _stations.value = stations
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}