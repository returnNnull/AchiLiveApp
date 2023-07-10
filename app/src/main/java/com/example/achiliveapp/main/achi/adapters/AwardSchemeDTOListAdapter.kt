package com.example.achiliveapp.main.achi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.achiliveapp.R
import com.example.achiliveapp.databinding.AchiListItemBinding
import com.example.achiliveapp.firebase.AwardSchemeDTO

class AwardSchemeDTOListAdapter : RecyclerView.Adapter<AwardSchemeDTOListAdapter.ViewHolder>() {

    var items: List<AwardSchemeDTO> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClick: (AwardSchemeDTO) -> Unit = {}
    fun itemClick(listener: (AwardSchemeDTO) -> Unit) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.achi_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.achi = items[position]
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