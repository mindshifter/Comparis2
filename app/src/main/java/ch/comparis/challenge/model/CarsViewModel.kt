package ch.comparis.challenge.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ch.comparis.challenge.repo.LocalCarsRepository
import ch.comparis.challenge.repo.Repository

class CarsViewModel(private val carsRepository: Repository) : ViewModel() {

    var cars = liveData {
        emit(carsRepository.fetchCars())
    }

    private var countInt: Int = 0
    val count = MutableLiveData<Int>()

    fun incrementCount() {
        cars.value?.find { it.mileage in 100..1000 }?.isFavorite = true
        count.postValue(countInt++)
    }
}