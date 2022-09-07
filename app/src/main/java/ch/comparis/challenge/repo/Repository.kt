package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car

interface Repository {
    fun fetchCars(): List<Car>
}