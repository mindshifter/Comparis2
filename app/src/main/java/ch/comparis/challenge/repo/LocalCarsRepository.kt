package ch.comparis.challenge.repo

import ch.comparis.challenge.model.Car

class LocalCarsRepository : Repository {
    override fun fetchCars(): List<Car> {
        return listOf(
            Car(1, "Audi", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2021AUC280001/2021AUC28000101.jpg", 18000),
            Car(2, "BMW", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2022BMS330001/2022BMS33000101.jpg", 1200),
            Car(3, "Toyota", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020TOS110017/2020TOS11001701.jpg", 580),
            Car(4, "Mazda", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020MAS060017/2020MAS06001701.jpg", 27000),
            Car(5, "Ford", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020FOT110136/2020FOT11013601.jpg", 115000),
            Car(6, "Honda", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020HOC020031/2020HOC02003101.jpg", 295000),
            Car(7, "Mercedes-Benz", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020MBC890063/2020MBC89006301.jpg", 6450),
            Car(8, "Chevrolet", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020CHT270029/2020CHT27002901.jpg", 15),
            Car(9, "Jeep", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020JES010017/2020JES01001701.jpg", 45340),
            Car(10, "Hyundai", "https://tdrresearch.azureedge.net/photos/chrome/Expanded/White/2020HYC020032/2020HYC02003201.jpg", 5400),
        )
    }
}