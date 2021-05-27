package co.anteia.anteiasdk.viewModel

import androidx.lifecycle.*
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.dto.SendEmailRequest
import co.anteia.anteiasdk.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ConfirmEmailViewModel (private val apiHelper: ApiHelper): ViewModel() {
    val data = DataProviderSingleton.instance



    fun sendEmail(email: String,token : String) = liveData(Dispatchers.IO) {

        val emailRequest = SendEmailRequest(email = email)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.sendEmail(email=emailRequest,token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun didEmail(token : String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.didEmail(token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}