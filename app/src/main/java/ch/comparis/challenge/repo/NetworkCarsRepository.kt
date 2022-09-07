package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car

class NetworkCarsRepository : Repository {
    override fun fetchCars(): List<Car> {
        //TODO get a list of cars from API
        return listOf()
    }
}