package co.anteia.anteiasdk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.databinding.FragmentGreetingBinding
import co.anteia.anteiasdk.databinding.FragmentInstructionsBinding
import co.anteia.anteiasdk.databinding.FragmentIntroBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class IntroFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIntroBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_intro, container, false)

        binding.nextButton.setOnClickListener {
            nextFragment()
        }
        return binding.root
        // Inflate the layout for this fragment

    }

    private fun nextFragment(){
        findNavController().navigate(R.id.action_introFragment_to_instructionsFragment)
    }






}