package com.example.achiliveapp.main.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.FragmentAddCategoryBinding
import com.example.achiliveapp.share.states.ScreenUiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddCategoryFragment : Fragment() {


    private val viewModel: AddCategoryViewModel by viewModels()
    private var binding: FragmentAddCategoryBinding? = null

    companion object{
        const val ID = "ID"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCategoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectScreenStates(view)
        collectCategory()

        arguments?.let {
            val id = it.getString(ID, null)
            viewModel.initById(id)
        }

        binding?.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            it.selectImg.setOnClickListener(selectImageBtnClickListener)
            it.appBar.setOnMenuItemClickListener(toolBarItemClickListener)
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

    private fun collectCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }
    }

    private fun collectScreenStates(view: View) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStates.collect {

                    when (it) {
                        is ScreenUiState.Success -> {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_addCategoryFragment_to_settingFragment)
                        }
                        is ScreenUiState.Error -> {
                            showSnackBar(it.message.message!!)
                        }

                        is ScreenUiState.Ready<*> -> {

                        }
                    }
                }
            }
        }
    }


    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.setImageUri(uri)
            } else {
                showSnackBar("Изображение не выбрано")
            }
        }


    private val createBtnClickListener: (View) -> Unit = {
        viewModel.save()
    }


    private val selectImageBtnClickListener: (View) -> Unit = {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun showSnackBar(text: String) {
        binding?.let {
            Snackbar.make(it.root, text, Snackbar.LENGTH_SHORT).show()
        }

    }


}