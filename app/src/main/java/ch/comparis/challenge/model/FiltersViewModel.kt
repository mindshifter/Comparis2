package ch.comparis.challenge.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.comparis.challenge.repo.Repository

class FiltersViewModel(private val carsRepository: Repository) : ViewModel() {

    val carFilter = CarsFilter()
    private val _filter = MutableLiveData<CarsFilter>()
    val filter: LiveData<CarsFilter> = _filter

    init {
        initFilter()
    }

    private fun initFilter() {
        carFilter.makes.addAll(carsRepository.fetchCars().map { MakeForFilter(it.make) })
        _filter.value = carFilter
    }

    fun getMakes(): MutableList<MakeForFilter> {
        return carFilter.makes
    }

    fun updateMakesFilter(checkedMakesArray: BooleanArray) {
        carFilter.makes.forEachIndexed { index, make ->
            make.isSelected = checkedMakesArray[index]
        }
        _filter.value = carFilter
    }

    fun filterFavorites(showFavorite: Boolean) {
        carFilter.showFavorite = showFavorite
        _filter.value = carFilter
    }
}