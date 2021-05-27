package co.anteia.anteiasdk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.databinding.FragmentCheckAddressBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CheckAddressFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCheckAddressBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_check_address, container, false)

        binding.nextButton.setOnClickListener {
            nextFragment()
        }
        binding.changeAddressTv.setOnClickListener{
            goToModifyAddress()
        }
        return binding.root
    }

    private fun nextFragment(){
        findNavController().navigate(R.id.action_checkAddressFragment_to_createPasswordFragment)
    }
    private fun goToModifyAddress(){
        findNavController().navigate(R.id.action_checkAddressFragment_to_changeAddressFragment)
    }


}