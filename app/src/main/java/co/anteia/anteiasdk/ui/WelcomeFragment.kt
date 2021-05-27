package co.anteia.anteiasdk.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.anteia.anteiasdk.R
import co.anteia.anteiasdk.databinding.FragmentGreetingBinding
import co.anteia.anteiasdk.databinding.FragmentInstructionsBinding
import co.anteia.anteiasdk.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var  binding : FragmentWelcomeBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_welcome, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("response", "Ha finalizado el proceso")
            requireActivity().setResult(Activity.RESULT_OK,intent)
            requireActivity().finish()
        }
    }
}