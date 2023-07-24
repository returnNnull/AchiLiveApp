package com.example.achiliveapp.main.achi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.AchiListItemBinding
import com.example.achiliveapp.firebase.AwardSchemeDTO
import com.example.achiliveapp.main.achi.data.AwardScheme

class AwardSchemeListAdapter : RecyclerView.Adapter<AwardSchemeListAdapter.ViewHolder>() {

    var items: List<AwardScheme> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClick: (AwardScheme) -> Unit = {}
    fun itemClick(listener: (AwardScheme) -> Unit) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.achi_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.award = items[position]
        holder.itemView.setOnClickListener {
            itemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = AchiListItemBinding.bind(view)

    }
}