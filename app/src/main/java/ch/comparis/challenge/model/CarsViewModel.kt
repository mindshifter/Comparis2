package ch.comparis.challenge.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.comparis.challenge.repo.Repository

class CarsViewModel(private val carsRepository: Repository) : ViewModel() {

    private var _cars = MutableLiveData<List<Car>>()
    var cars: LiveData<List<Car>> = _cars

    init {
        fetchCars()
        Log.d("ViewModel", "CarsViewModel Init")
    }

    private fun fetchCars() {
        _cars.value = carsRepository.fetchCars()
    }

    fun filterCars(filter: CarsFilter) {
        if (filter.showFavorite) {
            _cars.value = _cars.value?.filter { it.isFavorite }
        } else {
            fetchCars()
        }
        if (filter.makes.isNotEmpty()) {
            _cars.value = _cars.value?.filter { it.make.isSelected }
        }
        if (filter.mileageFrom != 0) {
            _cars.value?.filter { it.mileage in filter.mileageFrom..filter.mileageTo }
        }
    }

    fun addCarToFavorite(car: Car) {
        carsRepository.addCarToFavorite(car.make.name)
    }

    fun removeCarFromFavorite(car: Car) {
        carsRepository.removeCarFromFavorite(car.make.name)
    }
}