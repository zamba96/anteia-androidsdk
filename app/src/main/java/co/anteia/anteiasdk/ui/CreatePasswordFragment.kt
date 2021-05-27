package co.anteia.anteiasdk.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentCreatePasswordBinding
import co.anteia.anteiasdk.databinding.FragmentDataEntryBinding

import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.viewModel.CreatePasswordViewModel
import co.anteia.anteiasdk.viewModel.DataEntryFormViewModel


class CreatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreatePasswordBinding
    private lateinit var viewModel: CreatePasswordViewModel
    val data = DataProviderSingleton.instance

    private  val TAG = "DataEntryFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
            CreatePasswordViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_password, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.nextButton.setOnClickListener {
            nextFragment()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            setupAddPasswordObserver()
        }
    }

    private fun setupAddPasswordObserver(){
        viewModel.addPassword().observe(viewLifecycleOwner,{
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.e("response", resource.data.toString())
                        nextFragment()
                    }
                    Status.ERROR -> {
                        Log.e(TAG,"Error.. "+resource.message.toString())
                        binding.passwordConfirmTv.isEnabled = true
                        binding.passwordTv.isEnabled = true
                        binding.nextButton.isEnabled = true
                        Utilities.showSnackbar(requireActivity(),resource.message.toString())
                    }
                    Status.LOADING -> {
                        binding.passwordConfirmTv.isEnabled = false
                        binding.passwordTv.isEnabled = false
                        binding.nextButton.isEnabled = false
                        Log.e(TAG,"cargando..")
                    }
                }
            }
        })

    }

    private fun nextFragment(){
        findNavController().navigate(R.id.action_createPasswordFragment_to_welcomeFragment)
    }

}