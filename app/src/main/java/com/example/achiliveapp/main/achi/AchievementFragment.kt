package com.example.achiliveapp.main.achi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.achiliveapp.databinding.FragmentAchievementBinding
import com.example.achiliveapp.firebase.CategoriesSchemeDTO
import com.example.achiliveapp.main.achi.adapters.AchiViewPagerAdapter
import com.example.achiliveapp.main.states.ListUiState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class AchievementFragment : Fragment() {

    companion object {
        fun newInstance() = AchievementFragment()
    }

    private lateinit var viewModel: AchievementViewModel
    private var binding: FragmentAchievementBinding? = null

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementBinding.inflate(layoutInflater, container, false)
//        binding?.let {
//            viewPager = it.viewPager2
//            tabLayout = it.tabLayout
//        }
        return binding?.root
    }

    override fun onStart() {
        viewModel.update()
        super.onStart()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AchievementViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemState.collect { state ->

                    if(state.isSuccess()){
                       // attachTabsToViewPager(state.data)
                    }

                    if(state.isError()){

                    }

                }
            }
        }


    }

    private fun attachTabsToViewPager(list: List<CategoriesSchemeDTO>?) {
        list?.let {
            val items = it.toAdapterItems()
            viewPager.adapter = AchiViewPagerAdapter(this, items)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = items[position].name
            }.attach()
        }
    }

    private fun List<CategoriesSchemeDTO>.toAdapterItems(): List<AchiViewPagerAdapter.Item> {
        return this.map {
            AchiViewPagerAdapter.Item(it.id, it.name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}