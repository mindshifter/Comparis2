package ch.comparis.challenge.model

data class CarsFilter(
    var makes: MutableList<Make> = mutableListOf(),
    var showFavorite: Boolean = false,
    var mileageFrom: Int = MIN_MILEAGE,
    var mileageTo: Int = MAX_MILEAGE
) {
    companion object {
        const val MIN_MILEAGE = 0
        const val MAX_MILEAGE = 999999
    }
}