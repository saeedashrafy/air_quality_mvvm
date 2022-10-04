package com.ashr.cleanMvvmAir.presentation.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashr.cleanMvvmAir.databinding.ItemRowBinding
import com.ashr.cleanMvvmAir.domain.model.DomainCountry

class CountryAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<DomainCountry, CountryViewHolder>(CountryDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemBinding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CountryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }


}

class CountryViewHolder(private val binding: ItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(country: DomainCountry, onItemClick: (String) -> Unit) {
        binding.root.setOnClickListener {
            onItemClick(country.name)
        }
        binding.tvTitle.text = country.name
    }
}

private class CountryDiffCallBack : DiffUtil.ItemCallback<DomainCountry>() {
    override fun areItemsTheSame(oldItem: DomainCountry, newItem: DomainCountry): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DomainCountry, newItem: DomainCountry): Boolean =
        oldItem == newItem
}
