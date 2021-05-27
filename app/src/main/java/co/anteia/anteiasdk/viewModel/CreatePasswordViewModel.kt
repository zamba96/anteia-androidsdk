package co.anteia.anteiasdk.viewModel

import androidx.lifecycle.*
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.dto.AddPasswordRequest
import co.anteia.anteiasdk.utils.Resource
import kotlinx.coroutines.Dispatchers

/*
 Created by arenas on 06/05/21.
*/

class CreatePasswordViewModel (private val apiHelper: ApiHelper): ViewModel() {
    val password = MutableLiveData("")
    val passwordConfirm = MutableLiveData("")

    val errorPassword = MutableLiveData("")
    val errorPasswordConfirm = MutableLiveData("")
    val data = DataProviderSingleton.instance
    private var passwordsMatch : Boolean = false



    private fun isPasswordValid(it: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}".toRegex()
        return it.matches(regex = regex)
    }
    private fun passwordsMatches(pass: String,passConfirm: String): Boolean {

        return pass == passConfirm
    }


    fun addPassword() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val  pass = AddPasswordRequest(password.value)
            emit(Resource.success(data = apiHelper.addPassword(password = pass,token = data.token!!)))

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



    val valid = MediatorLiveData<Boolean>().apply {

        var passwordValid: Boolean? = null
        var passwordConfirmValid: Boolean? = null


        fun update() {
            val localPasswordValid = passwordValid
            val localPasswordConfirmValid = passwordConfirmValid

            if (passwordValid != null && passwordConfirmValid != null)
                this.value = localPasswordValid == true && localPasswordConfirmValid == true
        }

        addSource(password) {
            passwordValid = isPasswordValid(it)




            if (passwordValid == true) {
                if (passwordConfirm.value.isNullOrEmpty()){
                    errorPassword.value = ""
                }
                else {
                    val bothValids = passwordsMatches(password.value.toString(),passwordConfirm.value.toString())
                    if (bothValids){
                        errorPassword.value = ""
                    }else{
                        errorPassword.value = "Las contraseñas no coinciden"
                        passwordValid=false
                    }
                }
            } else {
                errorPassword.value = "Tu contraseña no cumple los criterios"
            }
            update()
        }

        addSource(passwordConfirm) {
            passwordConfirmValid = passwordsMatches(password.value.toString(),passwordConfirm.value.toString())

            if (passwordConfirmValid == true){
                errorPasswordConfirm.value = ""
            } else {
                errorPasswordConfirm.value = "Las contraseñas no coinciden"
            }
            update()
        }
    }
}