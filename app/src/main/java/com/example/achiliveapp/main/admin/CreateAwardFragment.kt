package com.example.achiliveapp.main.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.FragmentCreateAwardBinding
import com.example.achiliveapp.share.states.ScreenUiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CreateAwardFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAwardFragment()
        const val ID = "ID"
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

        val id = arguments?.getString(ID, null)
        viewModel = ViewModelProvider(this, CreateAwardViewModel(id))[CreateAwardViewModel::class.java]
        binding?.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            it.appBar.setOnMenuItemClickListener(toolBarItemClickListener)
            it.appBar.setNavigationOnClickListener(backClickListener)
            it.selectImg.setOnClickListener(selectImageBtnClickListener)
            collectScreenStates(it.root)
        }
    }


    private fun collectScreenStates(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.screenUiState.collect{
                    when(it){
                        is ScreenUiState.Success -> {
                            navigateToSetting(view)
                        }
                        is ScreenUiState.Error -> {
                            showSnackBar(it.message.message.toString())
                        }
                        is ScreenUiState.Loading -> {

                        }
                        is ScreenUiState.Ready<*> -> {

                        }
                    }
                }
            }
        }
    }





    private val toolBarItemClickListener: Toolbar.OnMenuItemClickListener = Toolbar.OnMenuItemClickListener {
        when(it.itemId){
            R.id.save -> {
                saveAward()
                true
            }
            else -> false
        }
    }

    private fun saveAward() {
        viewModel.save()
    }


    private fun showSnackBar(text: String) {
        binding?.let {
            Snackbar.make(it.root, text, Snackbar.LENGTH_SHORT).show()
        }

    }


    private val backClickListener :(View) -> Unit = {
        navigateToSetting(it)
    }

    private fun navigateToSetting(it: View) {
        Navigation.findNavController(it)
            .navigate(R.id.action_createAwardFragment_to_settingFragment)
    }


    private val selectImageBtnClickListener: (View) -> Unit = {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.setImg(uri)
        } else {
            showSnackBar("Изображение не выбрано")
        }
    }



}