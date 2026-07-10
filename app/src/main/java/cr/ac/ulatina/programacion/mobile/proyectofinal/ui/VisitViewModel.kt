package cr.ac.ulatina.programacion.mobile.proyectofinal.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cr.ac.ulatina.programacion.mobile.proyectofinal.data.repository.VisitRepository
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Pool
import cr.ac.ulatina.programacion.mobile.proyectofinal.model.Visit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class VisitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = VisitRepository(application)
    
    private val _allVisits = MutableStateFlow<List<Visit>>(emptyList())
    val allVisits: StateFlow<List<Visit>> = _allVisits.asStateFlow()

    private val _allPools = MutableStateFlow<List<Pool>>(emptyList())
    val allPools: StateFlow<List<Pool>> = _allPools.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _allVisits.value = repository.getAllVisits()
            _allPools.value = repository.getAllPools()
        }
    }

    fun insertVisit(visit: Visit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertVisit(visit)
        refreshData()
    }

    fun insertPool(pool: Pool) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPool(pool)
        refreshData()
    }
}
