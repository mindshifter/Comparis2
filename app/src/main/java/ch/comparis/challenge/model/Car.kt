package ch.comparis.challenge.model

data class Car(
    val make: Make,
    val avatar: String,
    val mileage: Int,
    var isFavorite: Boolean = false
)

data class Make(
    val id: Long,
    val name: String,
    var isSelected: Boolean = false
)
