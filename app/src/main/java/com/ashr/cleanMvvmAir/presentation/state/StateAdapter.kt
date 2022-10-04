package com.ashr.cleanMvvmAir.presentation.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashr.cleanMvvmAir.databinding.ItemRowBinding
import com.ashr.cleanMvvmAir.domain.model.DomainState

class StateAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<DomainState, StateViewHolder>(StateDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val itemBinding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }


}

class StateViewHolder(private val binding: ItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(state: DomainState, onItemClick: (String) -> Unit) {
        binding.root.setOnClickListener {
            onItemClick(state.name)
        }
        binding.tvTitle.text = state.name
    }
}

private class StateDiffCallBack : DiffUtil.ItemCallback<DomainState>() {
    override fun areItemsTheSame(oldItem: DomainState, newItem: DomainState): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: DomainState, newItem: DomainState): Boolean =
        oldItem == newItem
}
