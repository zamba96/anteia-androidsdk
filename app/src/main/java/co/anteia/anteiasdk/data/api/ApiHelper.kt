package co.anteia.anteiasdk.data.api

import co.anteia.anteiasdk.data.dto.*

/*
 Created by arenas on 10/05/21.
*/
class ApiHelper(private val apiService: ApiService) {

    suspend fun initialRegistration(body : InitialRegistrationRequest) = apiService.initialRegistration(body)
    suspend fun refreshClientToken(body : RefreshClientTokenRequest,credentials : String) = apiService.refreshClientToken(body,"Basic $credentials")

    suspend fun addInitialInformation(body : AddInitialInfoRequest, token :String) = apiService.addInitialInformation(body,"Bearer $token")
    suspend fun matiInit( token :String) = apiService.matiInit("Bearer $token")
    suspend fun sendPhotoFront( body:SendPhotoRequest,token :String) = apiService.sendPhotoFront(body,"Bearer $token")
    suspend fun sendPhotoBack( body:SendPhotoRequest,token :String) = apiService.sendPhotoBack(body,"Bearer $token")

    suspend fun sendPhotoFace( body:SendPhotoRequest,token :String) = apiService.sendPhotoFace(body,"Bearer $token")

    suspend fun sendEmail( email: SendEmailRequest, token :String) = apiService.sendEmail(email,"Bearer $token")
    suspend fun sendOtpMobile( phone: OtpRequest, token :String) = apiService.sendOtpMobile(phone,"Bearer $token")
    suspend fun confirmOtpMobile( code: ConfirmOtpRequest, token :String) = apiService.confirmOtpRequest(code,"Bearer $token")

    suspend fun modifyEmail( email: SendEmailRequest, token :String) = apiService.modifyEmail(email,"Bearer $token")
    suspend fun modifyPhone( phone: ModifyPhoneRequest, token :String) = apiService.modifyPhone(phone,"Bearer $token")
    suspend fun addPassword( password: AddPasswordRequest, token :String) = apiService.addPassword(password,"Bearer $token")


    suspend fun didEmail(  token :String) = apiService.didEmail("Bearer $token")
}