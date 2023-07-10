package com.example.achiliveapp.auth.ui.reg

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.auth.utils.getTextWithTrim
import com.example.achiliveapp.auth.ui.AuthUiState
import com.example.achiliveapp.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }


    private lateinit var viewModel: RegistrationViewModel
    private var binding: FragmentRegistrationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]

        binding?.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            it.regAndSignBtn.setOnClickListener(regAndSigningClickListener)
            it.backToSignIn.setOnClickListener(backToSignInClickListener)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.authState.collect{
                    if (it is AuthUiState.Error){
                        Snackbar.make(binding!!.root, it.e.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private val backToSignInClickListener: (View) -> Unit = {
        Navigation.findNavController(it).navigate(R.id.action_registrationFragment_to_signingFragment)
    }

    private val regAndSigningClickListener: (View) -> Unit = {
        binding?.let {
            viewModel.registration(
                it.email.getTextWithTrim(),
                it.pass.getTextWithTrim(),
                it.repeatPass.getTextWithTrim()
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}