package co.anteia.anteiasdk.ui.dataentry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.data.api.ApiHelper
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.data.api.RetrofitBuilder
import co.anteia.anteiasdk.databinding.FragmentDataEntryBinding
import co.anteia.anteiasdk.utils.Status
import co.anteia.anteiasdk.utils.Utilities
import co.anteia.anteiasdk.viewModel.BaseViewModelFactory
import co.anteia.anteiasdk.viewModel.DataEntryFormViewModel


class DataEntryFragment : Fragment() {

    private lateinit var binding: FragmentDataEntryBinding
    val data = DataProviderSingleton.instance
    private lateinit var viewModel: DataEntryFormViewModel
    private  val TAG = "DataEntryFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, BaseViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
            DataEntryFormViewModel::class.java)

        //TODO probar comportamiento del teclado
        //requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            if (viewModel.valid.value == true)
                sendInitialInfo()
            else
                Utilities.showSnackbar(requireActivity(),"Por favor, completa todos los campos")
        }
    }

    private fun sendInitialInfo() {
        viewModel.addInitialInformation().observe(requireActivity(), {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.e("response", resource.data.toString())
                        data.email= viewModel.email.value
                        data.phone = viewModel.phone.value
                        nextFragment()
                    }
                    Status.ERROR -> {
                        Log.e(TAG,"Error.. "+resource.message.toString())
                        binding.inputDataLinearLayout.visibility = View.VISIBLE
                        binding.animationView.visibility = View.INVISIBLE
                        binding.inputDataMessageTv.text = getString(R.string.complete_fields_message)
                        binding.nextButton.visibility = View.VISIBLE
                        Utilities.showSnackbar(requireActivity(),resource.message.toString())
                    }
                    Status.LOADING -> {
                        binding.inputDataLinearLayout.visibility = View.INVISIBLE
                        binding.nextButton.visibility = View.INVISIBLE
                        binding.animationView.visibility = View.VISIBLE
                        binding.inputDataMessageTv.text = getString(R.string.wait_a_moment)
                        Log.e(TAG,"cargando..")
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_data_entry, container, false
        )

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun nextFragment() {
        findNavController().navigate(R.id.action_dataEntryFragment_to_documentDetectionFragment)
    }
}