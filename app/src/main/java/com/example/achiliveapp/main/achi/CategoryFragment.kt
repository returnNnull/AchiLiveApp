package com.example.achiliveapp.main.achi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.achiliveapp.R

import com.example.achiliveapp.databinding.FragmentCategoryBinding
import com.example.achiliveapp.main.achi.adapters.AwardsListAdapterDelegate
import com.example.achiliveapp.main.achi.adapters.DefaultRecyclerViewAdapter
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {

    private var binding: FragmentCategoryBinding? = null
    private val viewModel by viewModels<CategoryFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkBundle()

        binding?.let {
            val adapter = DefaultRecyclerViewAdapter(AwardsListAdapterDelegate())
            it.awardsRecyclerView.adapter = adapter
            adapter.itemClick {

            }

            it.lifecycleOwner = this
            it.viewModel = viewModel

            it.toolbar.setNavigationOnClickListener{v ->
                navigateBack(v)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
               viewModel.screenUiState.collect{

               }
            }
        }
    }

    private fun checkBundle() {
        val argumentName = resources.getString(R.string.categoryId)
        val id = arguments?.getString(argumentName)
        id?.let {
            viewModel.initWithId(it)
        }
    }

    private fun navigateBack(view: View){
        Navigation.findNavController(view).navigate(R.id.action_categoryFragment_to_achievementListFragment)
    }

    override fun onStart() {
        super.onStart()
    }


}