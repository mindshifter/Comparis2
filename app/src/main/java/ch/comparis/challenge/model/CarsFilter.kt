package ch.comparis.challenge.model

data class CarsFilter(
    var makes: MutableList<MakeForFilter> = mutableListOf(),
    var showFavorite: Boolean = false,
    val mileageFrom: Int = 0,
    val mileageTo: Int = 0
)