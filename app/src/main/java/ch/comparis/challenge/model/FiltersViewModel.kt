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

    fun getAllMakes(): MutableList<Make> {
        return carsRepository.fetchCars().map { it.make }.toMutableList()
    }

    fun getFilterSelectedMakes(): MutableList<Make> {
        return carFilter.makes
    }

    fun updateMakesFilter(selectedMakes: MutableList<Make>) {
        carFilter.makes = selectedMakes
    }

    fun filterFavorites(showFavorite: Boolean) {
        carFilter.showFavorite = showFavorite
    }

    fun resetFilter() {
        carFilter = CarsFilter()
        updateFilter()
    }

    fun updateFilter() {
        _filter.value = carFilter
    }
}