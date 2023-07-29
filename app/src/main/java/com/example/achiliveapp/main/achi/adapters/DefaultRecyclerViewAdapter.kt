package com.example.achiliveapp.main.achi.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.achiliveapp.BR

class DefaultRecyclerViewAdapter<T>(private val delegate: RecyclerAdapterDelegate<T>) :
    RecyclerView.Adapter<DefaultRecyclerViewAdapter.ViewHolder>() {

    var items: List<T> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClick: (T) -> Unit = {}
    fun itemClick(listener: (T) -> Unit) {
        itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return delegate.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        delegate.setItem(holder, items[position])
        holder.itemView.setOnClickListener {
            itemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


    abstract class RecyclerAdapterDelegate<I> {
        abstract fun onCreateViewHolder(parent: ViewGroup): ViewHolder
        abstract fun setItem(holder: ViewHolder,item: I)


    }

}