package ua.diogo.cp.data.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.data.retrofit.repository.JorneyRepository

class JorneysViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val repository = JorneyRepository()
    private val _jorneys = MutableLiveData<Jorney>()
    val jorneys: LiveData<Jorney> = _jorneys

    fun fetchJorneys(trainId: String, date: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val jorneys = repository.getJorney(trainId, date)
                _jorneys.value = jorneys
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}
