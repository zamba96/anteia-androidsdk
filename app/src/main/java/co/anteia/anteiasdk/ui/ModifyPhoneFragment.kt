package co.anteia.anteiasdk.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentModifyPhoneBinding
import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.viewModel.ModifyPhoneViewModel


class ModifyPhoneFragment : Fragment() {
    private lateinit var viewModel: ModifyPhoneViewModel
    private lateinit var binding: FragmentModifyPhoneBinding
    val data = DataProviderSingleton.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
            .get(ModifyPhoneViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_modify_phone, container, false
        )

        if (!viewModel.data.phone.isNullOrBlank()) {
            viewModel.phone.value = viewModel.data.phone
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //OnClickListener
        binding.nextButton.setOnClickListener {
            setupModifyPhoneObserver(binding)
        }

        return binding.root
    }

    private fun setupModifyPhoneObserver(binding: FragmentModifyPhoneBinding){
        if (viewModel.valid.value == true) {
            viewModel.modifyPhone(viewModel.phone.value!!, data.token!!).observe(viewLifecycleOwner,{
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Utilities.showSnackbar(requireActivity(),"Número modificado")
                            Log.e("response", resource.data.toString())
                            data.phone=viewModel.phone.value
                            nextFragment()
                        }
                        Status.ERROR -> {
                            binding.animationView.visibility = View.INVISIBLE
                            binding.nextButton.visibility = View.VISIBLE
                            Utilities.showSnackbar(requireActivity(),resource.message.toString())
                        }
                        Status.LOADING -> {
                            binding.animationView.visibility = View.VISIBLE
                            binding.nextButton.visibility = View.INVISIBLE
                        }
                    }
                }
            })
        } else {
            Toast
                .makeText(context, "Ingresa un correo número", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun nextFragment() {
        findNavController().navigate(R.id.action_modifyPhoneFragment_to_confirmPhoneFragment)
    }
}