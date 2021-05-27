package co.anteia.anteiasdk.viewModel

import android.util.Log
import androidx.lifecycle.*
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.dto.ModifyPhoneRequest
import co.anteia.anteiasdk.utils.Resource
import kotlinx.coroutines.Dispatchers

class ModifyPhoneViewModel (private val apiHelper: ApiHelper) : ViewModel() {

    val data = DataProviderSingleton.instance
    val phone = MutableLiveData("")
    val errorPhone = MutableLiveData("")


    fun modifyPhone(phone: String,token : String) = liveData(Dispatchers.IO) {
        val modifyPhoneRequest = ModifyPhoneRequest(phone = phone)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.modifyPhone(phone=modifyPhoneRequest,token = token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun isPhoneValid(it: String): Boolean {
        if (it.matches("[0-9]{8,12}".toRegex()))
            return true
        return false
    }

    val valid = MediatorLiveData<Boolean>().apply {

        var phoneValid: Boolean? = null

        fun update() {
            val localEmailValid = phoneValid
            if (phoneValid != null)
                this.value = localEmailValid == true

        }

        addSource(phone) {
            Log.e("ModifyPhoneViewModel: ", "phoneChanged...$phone")
            phoneValid = isPhoneValid(it)
            if (phoneValid == true) {
                errorPhone.value = ""
            } else {
                errorPhone.value = "Ingresa un celular válido"
            }
            update()
        }
    }
}