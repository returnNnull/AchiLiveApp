package com.example.achiliveapp.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }


    private lateinit var viewModel: SettingViewModel
    private var binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingViewModel::class.java]

        binding?.let {
            it.logout.setOnClickListener(signOutClickListener)
            it.addCategory.setOnClickListener(addCategoryBtnClickListener)
            it.addAchi.setOnClickListener(addAwardClickListener)
        }
    }


    private val signOutClickListener :(View) -> Unit = {
        viewModel.signOut()
    }

    private val addAwardClickListener :(View) -> Unit = {
        Navigation.findNavController(it).navigate(R.id.action_settingFragment_to_createAwardFragment)
    }

    private val addCategoryBtnClickListener: (View) -> Unit =  {
        Navigation.findNavController(it).navigate(R.id.action_settingFragment_to_addCategoryFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}