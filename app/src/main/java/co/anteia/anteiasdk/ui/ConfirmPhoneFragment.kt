package co.anteia.anteiasdk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentConfirmPhoneBinding
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.ConfirmPhoneViewModel


class ConfirmPhoneFragment : Fragment() {

    private lateinit var viewModel: ConfirmPhoneViewModel
    val data = DataProviderSingleton.instance
    private lateinit var binding : FragmentConfirmPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
            .get(ConfirmPhoneViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            setupSendOtpMobileObserver()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterSmsCodeTv.text =
            resources.getString(R.string.enter_the_code, viewModel.data.phone)

        binding.nextButton.setOnClickListener {
            setupConfirmOtpMobileObserver(binding)
        }
        binding.modifyPhoneButton.setOnClickListener {
            goToModifyPhone()
        }
        binding.resendOtpMobileTv.setOnClickListener {
            setupSendOtpMobileObserver()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_confirm_phone, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setupConfirmOtpMobileObserver(binding: FragmentConfirmPhoneBinding) {
        viewModel.confirmOtpMobile(viewModel.code.value!!, data.token!!).observe(viewLifecycleOwner,{
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data!!.confirmed == true){
                            nextFragment()
                        }
                        else{
                            Utilities.showSnackbar(requireActivity(),"Código incorrecto")
                            binding.bottomButtonsLayout.visibility = View.VISIBLE
                            binding.animationView.visibility = View.INVISIBLE
                        }
                    }
                    Status.ERROR -> {
                        Utilities.showSnackbar(requireActivity(),resource.message.toString())
                        binding.bottomButtonsLayout.visibility = View.VISIBLE
                        binding.animationView.visibility = View.INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.bottomButtonsLayout.visibility = View.INVISIBLE
                        binding.animationView.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun nextFragment() {
        findNavController().navigate(R.id.action_confirmPhoneFragment_to_checkAddressFragment)
    }

    private fun setupSendOtpMobileObserver(){
        viewModel.sendOtpMobile(data.phone!!, data.token!!).observe(requireActivity(),{
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.bottomButtonsLayout.visibility = View.VISIBLE
                        binding.animationView.visibility = View.INVISIBLE
                        Utilities.showSnackbar(requireActivity(),"Mensaje enviado...")
                    }
                    Status.ERROR -> {
                        Utilities.showSnackbar(requireActivity(),resource.message.toString())
                        binding.bottomButtonsLayout.visibility = View.VISIBLE
                        binding.animationView.visibility = View.INVISIBLE
                    }
                    Status.LOADING -> {
                        binding.bottomButtonsLayout.visibility = View.INVISIBLE
                        binding.animationView.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun goToModifyPhone() {
        findNavController().navigate(R.id.action_confirmPhoneFragment_to_modifyPhoneFragment)
    }

}