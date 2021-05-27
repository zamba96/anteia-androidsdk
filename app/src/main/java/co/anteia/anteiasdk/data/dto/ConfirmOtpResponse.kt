package co.anteia.anteiasdk.data.dto

data class ConfirmOtpResponse(
    var confirmed: Boolean? = false,
    var message: String? = null
)
