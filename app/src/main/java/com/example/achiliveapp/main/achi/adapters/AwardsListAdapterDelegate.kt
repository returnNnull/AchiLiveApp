package com.example.achiliveapp.main.achi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.achiliveapp.databinding.AwardListItemBinding
import com.example.achiliveapp.domain.models.AwardsSchemeForPreview

class AwardsListAdapterDelegate :
    DefaultRecyclerViewAdapter.RecyclerAdapterDelegate<AwardsSchemeForPreview>() {
    override fun onCreateViewHolder(parent: ViewGroup): DefaultRecyclerViewAdapter.ViewHolder {
        val binding = AwardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DefaultRecyclerViewAdapter.ViewHolder(binding)
    }

    override fun setItem(holder: DefaultRecyclerViewAdapter.ViewHolder, item: AwardsSchemeForPreview) {
        (holder.binding as AwardListItemBinding).award = item
    }
}