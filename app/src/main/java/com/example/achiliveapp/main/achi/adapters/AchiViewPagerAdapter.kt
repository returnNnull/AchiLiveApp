package com.example.achiliveapp.main.achi.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.achiliveapp.main.achi.AchievementObjectFragment

class AchiViewPagerAdapter(fragment: Fragment, private val items: List<Item>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return AchievementObjectFragment.newInstance(items[position].id)
    }


    data class Item(
        val id: String,
        val name: String
    )
}