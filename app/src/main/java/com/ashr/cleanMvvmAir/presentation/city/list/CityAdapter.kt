package com.ashr.cleanMvvmAir.presentation.city.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashr.cleanMvvmAir.databinding.ItemRowBinding
import com.ashr.cleanMvvmAir.domain.model.DomainCity

class CityAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<DomainCity, CityViewHolder>(DomainDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemBinding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }


}

class CityViewHolder(private val binding: ItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(state: DomainCity, onItemClick: (String) -> Unit) {
        binding.root.setOnClickListener {
            onItemClick(state.name)
        }
        binding.tvTitle.text = state.name
    }
}

private class DomainDiffCallBack : DiffUtil.ItemCallback<DomainCity>() {
    override fun areItemsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean =
        oldItem == newItem
}
