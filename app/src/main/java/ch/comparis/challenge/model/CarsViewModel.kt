package ch.comparis.challenge.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.comparis.challenge.repo.Repository

class CarsViewModel(private val carsRepository: Repository) : ViewModel() {

    private var _cars = MutableLiveData<List<Car>>()
    var cars: LiveData<List<Car>> = _cars

    init {
        fetchCars()
    }

    private fun fetchCars() {
        _cars.value = carsRepository.fetchCars()
    }

    fun filterCars(filter: CarsFilter) {
        _cars.value = carsRepository.filterCars(filter)
    }

    fun addCarToFavorite(car: Car) {
        carsRepository.addCarToFavorite(car.make.name)
    }

    fun removeCarFromFavorite(car: Car) {
        carsRepository.removeCarFromFavorite(car.make.name)
    }
}