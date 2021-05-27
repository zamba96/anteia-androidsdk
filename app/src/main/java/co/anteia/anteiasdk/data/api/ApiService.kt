package co.anteia.anteiasdk.data.api

import co.anteia.anteiasdk.data.dto.*
import retrofit2.http.*

/*
 Created by arenas on 10/05/21.
*/
interface ApiService {

    @POST("registerFlow/initialRegistration/")
    suspend fun initialRegistration(
        @Body initialRegistrationRequest: InitialRegistrationRequest
    ): InitialRegistrationResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("auth/refreshClientToken/")
    suspend fun refreshClientToken(
        @Body body : RefreshClientTokenRequest,
        @Header("Authorization") credentials: String

    ):RefreshClientTokenResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("registerFlow/addInitialInformation/")
    suspend fun addInitialInformation(
        @Body initialInfoRequest: AddInitialInfoRequest,
        @Header("Authorization") token: String
    )

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("mati/init/")
    suspend fun matiInit(@Header("Authorization") token: String
    )


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("mati2/sendPhotoFront/")
    suspend fun sendPhotoFront(
        @Body file : SendPhotoRequest,
        @Header("Authorization") token: String
    ):SendPhotoResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("mati2/sendPhotoBack/")
    suspend fun sendPhotoBack(
        @Body file : SendPhotoRequest,
        @Header("Authorization") token: String
    ):SendPhotoResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("mati2/sendPhotoFace/")
    suspend fun sendPhotoFace(
        @Body file : SendPhotoRequest,
        @Header("Authorization") token: String
    ):SendPhotoResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("otp/sendEmail/")
    suspend fun sendEmail(
        @Body email : SendEmailRequest,
        @Header("Authorization") token: String
    )

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("otp/didEmail/")
    suspend fun didEmail(
        @Header("Authorization") token: String
    ):DidEmailResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("registerFlow/modifyEmail/")
    suspend fun modifyEmail(
        @Body email : SendEmailRequest,
        @Header("Authorization") token: String
    )
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("registerFlow/modifyPhone/")
    suspend fun modifyPhone(
        @Body phone : ModifyPhoneRequest,
        @Header("Authorization") token: String
    )
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("otp/sendOtpMobile/")
    suspend fun sendOtpMobile(
        @Body phone : OtpRequest,
        @Header("Authorization") token: String
    )
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("otp/confirmOtpMobile/")
    suspend fun confirmOtpRequest(
        @Body code : ConfirmOtpRequest,
        @Header("Authorization") token: String
    ):ConfirmOtpResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("registerFlow/addPassword/")
    suspend fun addPassword(
        @Body password : AddPasswordRequest,
        @Header("Authorization") token: String
    )
}