package ch.comparis.challenge.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.comparis.challenge.repo.Repository

class CarsViewModel(private val carsRepository: Repository) : ViewModel() {

    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> = _cars

    private val _filter = MutableLiveData<CarsFilter>()
    val filter: LiveData<CarsFilter> = _filter

    init {
        fetchCars()
    }

    private fun fetchCars() {
        _cars.value = carsRepository.fetchCars()
    }

    fun filterByMileage(filter: CarsFilter) {
        _cars.value = carsRepository.filterByMileage(filter.mileageFrom, filter.mileageTo)
    }
}