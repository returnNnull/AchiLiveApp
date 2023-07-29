package com.example.achiliveapp.main.achi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.achiliveapp.databinding.CategoryListItemBinding
import com.example.achiliveapp.domain.models.CategoryWithRating


class CategoryListAdapterDelegate : DefaultRecyclerViewAdapter.RecyclerAdapterDelegate<CategoryWithRating>() {
    override fun onCreateViewHolder(parent: ViewGroup): DefaultRecyclerViewAdapter.ViewHolder {
        val view = CategoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultRecyclerViewAdapter.ViewHolder(view)
    }

    override fun setItem(holder: DefaultRecyclerViewAdapter.ViewHolder, item: CategoryWithRating) {
        (holder.binding as CategoryListItemBinding).category = item
    }


}