package co.anteia.anteiasdk.data.dto

data class UserLocation(
    var lat: Double? = null,
    var lon: Double? = null,
    var trusted: Boolean = false,
    var userId: String? = null,
    var locality: String? = null,
    var adminArea: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postalCode: String? = null,
    var address: String? = null
)
