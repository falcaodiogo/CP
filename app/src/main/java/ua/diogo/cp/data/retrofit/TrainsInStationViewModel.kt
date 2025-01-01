package ua.diogo.cp.data.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.diogo.cp.data.retrofit.entity.TrainsInStation
import ua.diogo.cp.data.retrofit.repository.TrainsInStationsRepository

class TrainsInStationViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val repository = TrainsInStationsRepository()
    private val _trainsInStations = MutableLiveData<List<TrainsInStation>>()
    val trainsInStation: LiveData<List<TrainsInStation>> = _trainsInStations

    fun fetchTrainsInStation(stationId: String) {
        viewModelScope.launch {
            try {
                val trainsInStation = repository.getTrainsInStations(stationId)
                _trainsInStations.value = trainsInStation
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
