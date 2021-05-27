@file:Suppress("UNCHECKED_CAST")

package co.anteia.anteiasdk.viewModel

/*
 Created by arenas on 10/05/21.
*/

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import co.anteia.anteiasdk.data.api.ApiHelper


class BaseViewModelFactory( vararg params: Any) : NewInstanceFactory() {
    private val mParams: Array<Any> = params as Array<Any>
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TermsAndConditionsViewModel::class.java -> {
                TermsAndConditionsViewModel( mParams[0] as ApiHelper) as T
            }
            DataEntryFormViewModel::class.java -> {
                DataEntryFormViewModel( mParams[0] as ApiHelper) as T
            }
            DetectionViewModel::class.java -> {
                DetectionViewModel( mParams[0] as ApiHelper) as T
            }
            ConfirmEmailViewModel::class.java -> {
                ConfirmEmailViewModel( mParams[0] as ApiHelper) as T
            }
            ModifyEmailViewModel::class.java -> {
                ModifyEmailViewModel( mParams[0] as ApiHelper) as T
            }
            ConfirmPhoneViewModel::class.java -> {
                ConfirmPhoneViewModel( mParams[0] as ApiHelper) as T
            }
            ModifyPhoneViewModel::class.java -> {
                ModifyPhoneViewModel( mParams[0] as ApiHelper) as T
            }
            CreatePasswordViewModel::class.java -> {
                CreatePasswordViewModel( mParams[0] as ApiHelper) as T
            }
            else -> {
                super.create(modelClass)
            }
        }
    }

}
