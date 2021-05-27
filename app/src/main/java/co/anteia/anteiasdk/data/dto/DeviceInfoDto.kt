package co.anteia.anteiasdk.data.dto

data class DeviceInfoDto(
    var os: String? = null,
    var osVersion: String? = null,
    var deviceId: String? = null,
    var userId: String? = null,
    var screenHeigh: Int? = null,
    var screenWidth: Int? = null,
    var trusted: Boolean? = null,
    var type: String? = null,
    var ip: String? = null,
    var numCpus: String? = null
)
