package co.anteia.anteiasdk.ui.termsandconditions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentTermsAndConditionsBinding
import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.viewModel.TermsAndConditionsViewModel

class TermsAndConditionsActivity : AppCompatActivity() {

    private lateinit var viewModel: TermsAndConditionsViewModel
    val data = DataProviderSingleton.instance
    private lateinit var binding: FragmentTermsAndConditionsBinding

    private val TAG = "TermsAndConditionsFragment"
    private lateinit var anteiaToken: String
    private lateinit var registrationID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_terms_and_conditions)

        viewModel = ViewModelProvider(this, BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(TermsAndConditionsViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.fragment_terms_and_conditions)
        binding.lifecycleOwner = this

        binding.acceptButton.setOnClickListener {
            if (viewModel.marked.value == true)
                acceptButtonClicked()
            else{
                Utilities.showSnackbar(this, "Debe aceptar los términos para continuar")
            }
        }

        binding.rejectButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("response", "Has cancelado el proceso")
            setResult(Activity.RESULT_CANCELED,intent)
            finish()
        }
        binding.checkBox.setOnCheckedChangeListener { _, b: Boolean ->
            viewModel.marked.value = b
        }

    }

    private fun acceptButtonClicked() {

        viewModel.initialRegistration().observe(this,{
            it.let { resource ->
                when (resource.status) { // primero chequeamos el registro
                    Status.SUCCESS -> {
                        Log.e("response", resource.data.toString())
                        data.token = resource.data?.userToken
                        anteiaToken = resource.data?.userToken!!
                        data.registrationId = resource.data.registrationId
                        registrationID = resource.data.registrationId!!
                        viewModel.initMati(data.token!!).observe(this, { secondRequest ->
                            secondRequest.let {
                                Log.e("Token", resource.data!!.userToken!!)
                                when (secondRequest.status){ // despues adentro chequeamos el inicio de mati
                                    Status.SUCCESS->{
                                        nextFragment()
                                    }
                                    Status.ERROR -> {
                                        Log.e(TAG, "error..")
                                        binding.termsLayout.visibility = View.VISIBLE
                                        binding.animationView.visibility = View.INVISIBLE
                                        binding.buttonsRelativeLayout.visibility = View.VISIBLE
                                        Utilities.showSnackbar(this, resource.message.toString())
                                    }
                                    Status.LOADING -> {
                                        Log.e(TAG, "cargando..")
                                        binding.termsLayout.visibility = View.INVISIBLE
                                        binding.buttonsRelativeLayout.visibility = View.INVISIBLE
                                        binding.animationView.visibility = View.VISIBLE
                                    }
                                }
                            }
                        })
                    }

                    Status.ERROR -> {
                        Log.e(TAG, "error..")
                        binding.termsLayout.visibility = View.VISIBLE
                        binding.animationView.visibility = View.INVISIBLE
                        binding.buttonsRelativeLayout.visibility = View.VISIBLE
                        Utilities.showSnackbar(this, resource.message.toString())
                    }
                    Status.LOADING -> {
                        Log.e(TAG, "cargando..")
                        binding.termsLayout.visibility = View.INVISIBLE
                        binding.buttonsRelativeLayout.visibility = View.INVISIBLE
                        binding.animationView.visibility = View.VISIBLE
                    }
                }
            }
        })
    }




    private fun nextFragment() {
        val intent = Intent()
        intent.putExtra("response", "Ha finalizado el proceso")
        intent.putExtra("registrationId",registrationID)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}