package co.anteia.anteiasdk.viewModel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.dto.SendEmailRequest
import co.anteia.anteiasdk.utils.Resource
import kotlinx.coroutines.Dispatchers

class ModifyEmailViewModel (private val apiHelper: ApiHelper): ViewModel() {
    val data = DataProviderSingleton.instance
    val email = MutableLiveData("")
    val errorEmail = MutableLiveData("")

    fun modifyEmail(email: String,token : String) = liveData(Dispatchers.IO) {

        val modifyEmailRequest = SendEmailRequest(email = email)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.modifyEmail(email=modifyEmailRequest,token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }




    private fun isEmailValid(it: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    val valid = MediatorLiveData<Boolean>().apply {

        var emailValid: Boolean? = null

        fun update() {
            val localEmailValid = emailValid
            if (emailValid != null)
                this.value = localEmailValid == true

        }

        addSource(email) {
          //  Log.e("DataEntryViewModel: ", "emailChanged...$email")
            emailValid = isEmailValid(it)
            if (emailValid == true) {
                errorEmail.value = ""
            } else {
                errorEmail.value = "Formato de correo inválido"
            }
            update()
        }


    }
}