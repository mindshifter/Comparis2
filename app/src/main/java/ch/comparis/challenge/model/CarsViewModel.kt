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
        var cacheCars = carsRepository.fetchCars()
        if (filter.showFavorite) {
            cacheCars = cacheCars.filter { it.isFavorite }
        }
        if (filter.makes.isNotEmpty()) {
            cacheCars = cacheCars.filter { it.make.isSelected }
        }
        cacheCars = cacheCars.filter { it.mileage in filter.mileageFrom..filter.mileageTo }
        _cars.value = cacheCars
    }

    fun addCarToFavorite(car: Car) {
        carsRepository.addCarToFavorite(car.make.name)
    }

    fun removeCarFromFavorite(car: Car) {
        carsRepository.removeCarFromFavorite(car.make.name)
    }
}