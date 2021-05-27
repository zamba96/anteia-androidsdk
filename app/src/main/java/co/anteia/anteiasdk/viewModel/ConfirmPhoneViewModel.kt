package co.anteia.anteiasdk.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.dto.ConfirmOtpRequest
import co.anteia.anteiasdk.data.dto.OtpRequest
import co.anteia.anteiasdk.utils.Resource
import kotlinx.coroutines.Dispatchers

class ConfirmPhoneViewModel (private val apiHelper: ApiHelper): ViewModel() {
    val data = DataProviderSingleton.instance
    val code = MutableLiveData("")


    fun sendOtpMobile(phone: String,token : String) = liveData(Dispatchers.IO) {

        val otpMobileRequest = OtpRequest(phone = phone)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.sendOtpMobile(phone = otpMobileRequest,token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun confirmOtpMobile(code: String,token : String) = liveData(Dispatchers.IO) {

        val otpMobileRequest = ConfirmOtpRequest(code = code)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.confirmOtpMobile(code = otpMobileRequest,token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}