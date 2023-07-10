package com.example.achiliveapp.main.admin

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.auth.utils.getTextWithTrim
import com.example.achiliveapp.databinding.FragmentAddCategoryBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class AddCategoryFragment : Fragment() {


    private lateinit var viewModel: AddCategoryViewModel
    private var binding: FragmentAddCategoryBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCategoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[AddCategoryViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStates.collect {

                    when (it) {
                        is AddCategoryViewModel.UiStates.Success -> {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_addCategoryFragment_to_settingFragment)
                        }
                        is AddCategoryViewModel.UiStates.Error -> {
                            showSnackBar(it.e.message!!)
                        }
                        is AddCategoryViewModel.UiStates.ImageUploading -> {

                        }
                        is AddCategoryViewModel.UiStates.CategoryUploading -> {

                        }

                        is AddCategoryViewModel.UiStates.Ready -> {

                        }
                    }
                }
            }
        }

        binding?.let {
            it.selectImg.setOnClickListener(selectImageBtnClickListener)
            it.categoryCreateBtn.setOnClickListener(createBtnClickListener)
        }
    }

    private fun showLoading(text: String) {
        binding?.let {
            val stub = it.loadingStubImport.inflate()
            val textView = stub.findViewById<TextView>(R.id.progressText)
            textView.text = text
        }
    }


    private fun closeLoading() {
        binding?.let {
            it.loadingStubImport.visibility = View.INVISIBLE
        }
    }

    private val createBtnClickListener: (View) -> Unit = {
        binding?.let {
            val name = it.categoryName.getTextWithTrim()
            val about = it.categoryAbout.getTextWithTrim()
            viewModel.create(name, about)
        }

    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {result ->
        if (result != null) {
            viewModel.setImageUri(result)
        } else {

        }
    }

    private val selectImageBtnClickListener: (View) -> Unit = {
       pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun setImg(uri: Uri) {
        binding?.let {
            Picasso.get().load(uri).into(it.selectImg, object : Callback {
                override fun onSuccess() {
                    it.selectImg.scaleType = ImageView.ScaleType.CENTER_CROP
                }

                override fun onError(e: Exception?) {
                    showSnackBar("Изображение не загружено")
                }

            })
        }

    }

    private fun showSnackBar(text: String) {
        binding?.let {
            Snackbar.make(it.root, text, Snackbar.LENGTH_SHORT).show()
        }

    }


}