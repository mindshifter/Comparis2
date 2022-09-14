package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car
import ch.comparis.challenge.model.CarsFilter
import ch.comparis.challenge.model.Make

interface Repository {
    fun fetchCars(): List<Car>
    fun filterCars(filter: CarsFilter):List<Car>
    fun addCarToFavorite(makeName: String)
    fun removeCarFromFavorite(makeName: String)
}