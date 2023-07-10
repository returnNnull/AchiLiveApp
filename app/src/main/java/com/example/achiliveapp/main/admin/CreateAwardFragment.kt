package com.example.achiliveapp.main.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.FragmentCreateAwardBinding

class CreateAwardFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAwardFragment()
    }

    private lateinit var viewModel: CreateAwardViewModel
    private var binding: FragmentCreateAwardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAwardBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CreateAwardViewModel::class.java]
    }

}