package com.example.achiliveapp.main.achi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.FragmentAchievementBinding
import com.example.achiliveapp.main.achi.adapters.AwardsGridAdapterDelegate
import com.example.achiliveapp.main.achi.adapters.CategoryListAdapterDelegate
import com.example.achiliveapp.main.achi.adapters.DefaultRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AchievementFragment : Fragment() {

    private val viewModel by viewModels<AchievementViewModel>()
    private var binding: FragmentAchievementBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            initCategoriesList(it)
            initAwardsList(it)
        }

    }

    private fun initCategoriesList(binding: FragmentAchievementBinding) {
        val categoriesAdapter = DefaultRecyclerViewAdapter(CategoryListAdapterDelegate())
        categoriesAdapter.itemClick { category ->
            navigateToCategoryFragment(binding.categoryRecyclerView, category.id)
        }
        binding.categoryRecyclerView.adapter = categoriesAdapter
    }

    private fun initAwardsList(binding: FragmentAchievementBinding) {
        val awardsAdapter = DefaultRecyclerViewAdapter(AwardsGridAdapterDelegate())
        awardsAdapter.itemClick { award ->
            navigateToAwardDetails(binding.awardsRecyclerView, award.id)
        }
        binding.awardsRecyclerView.adapter = awardsAdapter
    }


    private fun navigateToCategoryFragment(view: View, id: String) {
        Navigation.findNavController(view).navigate(
            R.id.action_achievementListFragment_to_categoryFragment,
            createBundleWithId(R.string.categoryId, id)
        )
    }

    private fun navigateToAwardDetails(view: View, id: String) {
        Navigation.findNavController(view).navigate(
            R.id.action_achievementListFragment_to_awardDetailsFragment,
            createBundleWithId(R.string.awardsId, id)
        )
    }

    private fun createBundleWithId(resourceId: Int, value: String): Bundle {
        return Bundle().apply {
            this.putString(resources.getString(resourceId), value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}