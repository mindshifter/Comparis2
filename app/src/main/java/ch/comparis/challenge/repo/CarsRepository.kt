package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car
import ch.comparis.challenge.model.Make

class CarsRepository : Repository {
    override fun fetchCars(): List<Car> {
        //TODO get a list of cars from data base (ex: Room)
        return listOf()
    }

    override fun filterByFavorite(): List<Car> {
        TODO("Not yet implemented")
    }

    override fun filterByMileage(from: Int, to: Int): List<Car> {
        TODO("Not yet implemented")
    }

    override fun filterByMakes(makes: List<Make>): List<Car> {
        TODO("Not yet implemented")
    }
}