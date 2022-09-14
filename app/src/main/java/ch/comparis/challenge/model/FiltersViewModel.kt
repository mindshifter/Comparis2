package ch.comparis.challenge.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.comparis.challenge.repo.Repository

class FiltersViewModel(private val carsRepository: Repository) : ViewModel() {

    private var carFilter = CarsFilter()
    private val _filter = MutableLiveData<CarsFilter>()
    val filter: LiveData<CarsFilter> = _filter

    init {
        initFilter()
    }

    private fun initFilter() {
        _filter.value = carFilter
    }

    fun getAvailableMakes(): List<SelectedMake> {
        return carsRepository.fetchCars().map { SelectedMake(it.make) }
    }

    fun resetFilter() {
        carFilter = CarsFilter()
        initFilter()
    }

    fun updateFilter(carsFilter: CarsFilter) {
        carFilter = carsFilter
        _filter.value = carsFilter
    }
}