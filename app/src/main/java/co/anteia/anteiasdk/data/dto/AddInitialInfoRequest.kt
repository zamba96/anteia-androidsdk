package co.anteia.anteiasdk.data.dto

data class AddInitialInfoRequest(
    var lastName: String? = null,
    var cellphone: String? = null,
    var email: String? = null,
    var identification: String? = null
)
