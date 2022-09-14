package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car
import ch.comparis.challenge.model.CarsFilter
import ch.comparis.challenge.model.Make

class CarsRepository : Repository {
    private val cars = listOf(
        Car(Make(1, "Audi"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2021AUC280001/2021AUC28000101.jpg", 18000),
        Car(Make(2, "BMW"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2022BMS330001/2022BMS33000101.jpg", 1200),
        Car(Make(3, "Toyota"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020TOS110017/2020TOS11001701.jpg", 580, true),
        Car(Make(4, "Mazda"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020MAS060017/2020MAS06001701.jpg", 27000),
        Car(Make(5, "Ford"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020FOT110136/2020FOT11013601.jpg", 115000),
        Car(Make(6, "Honda"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020HOC020031/2020HOC02003101.jpg", 295000),
        Car(Make(7, "Mercedes-Benz"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020MBC890063/2020MBC89006301.jpg", 6450, true),
        Car(Make(8, "Chevrolet"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020CHT270029/2020CHT27002901.jpg", 15),
        Car(Make(9, "Jeep"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020JES010017/2020JES01001701.jpg", 45340),
        Car(Make(10, "Hyundai"), "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020HYC020032/2020HYC02003201.jpg", 5400),
    )

    override fun fetchCars(): List<Car> {
        return cars
    }

    override fun filterCars(filter: CarsFilter): List<Car> {
        var cacheCars = fetchCars()
        if (filter.showFavorite) {
            cacheCars = cacheCars.filter { it.isFavorite }
        }
        if (filter.selectedMakes.isNotEmpty()) {
            cacheCars = filter.selectedMakes.flatMap { selectedMake -> cacheCars.filter { selectedMake.make.id == it.make.id } }
        }
        cacheCars = cacheCars.filter { it.mileage in filter.mileageFrom..filter.mileageTo }
        return cacheCars
    }

    override fun addCarToFavorite(makeName: String) {
        cars.find { it.make.name == makeName }?.isFavorite = true
    }

    override fun removeCarFromFavorite(makeName: String) {
        cars.find { it.make.name == makeName }?.isFavorite = false
    }
}