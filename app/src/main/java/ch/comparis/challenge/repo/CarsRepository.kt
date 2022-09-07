package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car

class CarsRepository : Repository {
    override fun fetchCars(): List<Car> {
        //TODO get a list of cars from data base (ex: Room)
        return listOf()
    }
}