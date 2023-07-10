package com.example.achiliveapp.auth.ui.signin

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
import com.example.achiliveapp.databinding.FragmentSigninBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SigningFragment : Fragment() {

    companion object {
        fun newInstance() = SigningFragment()
    }

    private lateinit var viewModel: SigningViewModel
    private var binding: FragmentSigninBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SigningViewModel::class.java]
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collect {
                    if (it is AuthUiState.Error) {
                        Snackbar.make(binding!!.root, it.e.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding!!.loginBtn.setOnClickListener {
            viewModel.login(
                binding!!.email.getTextWithTrim(),
                binding!!.pass.getTextWithTrim()
            )
        }

        binding!!.regBtn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_signingFragment_to_registrationFragment)
        }


    }


}