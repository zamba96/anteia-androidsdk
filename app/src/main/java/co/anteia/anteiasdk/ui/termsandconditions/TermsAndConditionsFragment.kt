@file:Suppress("DEPRECATION")

package co.anteia.anteiasdk.ui.termsandconditions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentTermsAndConditionsBinding
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.TermsAndConditionsViewModel


class TermsAndConditionsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var viewModel: TermsAndConditionsViewModel
    val data = DataProviderSingleton.instance
    private lateinit var binding: FragmentTermsAndConditionsBinding


    private val TAG = "TermsAndConditionsFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(TermsAndConditionsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acceptButton.setOnClickListener {
            if (viewModel.marked.value == true)
                acceptButtonClicked()
            else{
                Utilities.showSnackbar(requireActivity(), "Debe aceptar los términos para continuar")
            }
        }

        binding.rejectButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("response", "Has cancelado el proceso")
            requireActivity().setResult(Activity.RESULT_CANCELED,intent)
            requireActivity().finish()
        }
        binding.checkBox.setOnCheckedChangeListener { _, b: Boolean ->
            viewModel.marked.value = b
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_terms_and_conditions, container, false
        )
        binding.lifecycleOwner = this

        return binding.root
    }


    private fun nextFragment() {
        findNavController().navigate(R.id.action_termsAndConditionsFragment_to_greetingFragment)
    }

    private fun acceptButtonClicked(){
        viewModel.initialRegistration().observe(requireActivity(),{
            it.let { resource ->
                when (resource.status) { // primero chequeamos el registro
                    Status.SUCCESS -> {
                        Log.e("response", resource.data.toString())
                        data.token = resource.data?.userToken
                        data.registrationId = resource.data?.registrationId
                        viewModel.initMati(data.token!!).observe(requireActivity(), { secondRequest ->
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
                                        Utilities.showSnackbar(requireActivity(), resource.message.toString())
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
                        Utilities.showSnackbar(requireActivity(), resource.message.toString())
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
}