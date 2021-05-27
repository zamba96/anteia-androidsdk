package co.anteia.anteiasdk.viewModel

import android.util.Patterns
import androidx.lifecycle.*
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.utils.Resource
import co.anteia.anteiasdk.data.dto.AddInitialInfoRequest
import kotlinx.coroutines.Dispatchers

/*
 Created by arenas on 06/05/21.
*/

class DataEntryFormViewModel (private val apiHelper: ApiHelper): ViewModel() {
    val email = MutableLiveData("")
    val dni = MutableLiveData("")
    val phone = MutableLiveData("")
    val lastname = MutableLiveData("")
    val errorEmail = MutableLiveData("")
    val errorlname = MutableLiveData("")
    val errorPhoneNumber = MutableLiveData("")
    val errorDni = MutableLiveData("")
    val data = DataProviderSingleton.instance



    private fun isEmailValid(it: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    private fun isDniValid(it: String): Boolean {
        if (it.matches("[0-9]{7,10}".toRegex()))
            return true
        return false
    }

    private fun isLastNameValid(it: String): Boolean {
        if (it.matches("^([^0-9]*)\$".toRegex()) && it.length > 2)
            return true
        return false
    }

    private fun isPhoneValid(it: String): Boolean {
        if (it.matches("[0-9]{8,12}".toRegex()))
            return true
        return false
    }


    fun addInitialInformation() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val  body = AddInitialInfoRequest(lastName = lastname.value,email = email.value,identification = dni.value,cellphone = phone.value)
            emit(Resource.success(data = apiHelper.addInitialInformation(body = body,token = data.token!!)))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.toString()))
        }
    }

    val valid = MediatorLiveData<Boolean>().apply {

        var lastNameValid: Boolean? = null
        var phoneValid: Boolean? = null
        var emailValid: Boolean? = null
        var dniValid: Boolean? = null

        fun update() {
            val localEmailValid = emailValid
            val localDniValid = dniValid
            val localPhoneValid = phoneValid
            val localLastNameValid = lastNameValid
            if (emailValid != null && dniValid != null && lastNameValid != null && phoneValid != null && lastNameValid != null)
                this.value =
                    localDniValid == true && localEmailValid == true && localPhoneValid == true && localLastNameValid == true
        }

        addSource(phone) {
            phoneValid = isPhoneValid(it)
            if (phoneValid == true) {
                errorPhoneNumber.value = ""
            } else {
                errorPhoneNumber.value = "Debe completar todos los datos para continuar"
            }
            update()
        }

        addSource(lastname) {
            lastNameValid = isLastNameValid(it)
            if (lastNameValid == true) {
                errorlname.value = ""
            } else {
                errorlname.value = "No debe haber números"
            }
            update()
        }

        addSource(email) {
            emailValid = isEmailValid(it)
            if (emailValid == true) {
                errorEmail.value = ""
            } else {
                errorEmail.value = "Formato de correo inválido"
            }
            update()
        }

        addSource(dni) {
            dniValid = isDniValid(it)
            if (dniValid == true) {
                errorDni.value = ""
            } else {
                errorDni.value = "Debe completar todos los datos para continuar"
            }
            update()
        }

    }


}