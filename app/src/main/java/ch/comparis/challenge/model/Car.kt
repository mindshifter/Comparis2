package ch.comparis.challenge.model

data class Car(
    val id: Long,
    val make: String,
    val avatar: String,
    val mileage: Int,
    var isFavorite: Boolean = false
)
