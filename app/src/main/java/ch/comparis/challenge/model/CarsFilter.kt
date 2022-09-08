package ch.comparis.challenge.model

data class CarsFilter(
    var makes: MutableList<Make> = mutableListOf(),
    var showFavorite: Boolean = false,
    var mileageFrom: Int = 0,
    var mileageTo: Int = 999999
)