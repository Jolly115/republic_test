package com.gupta.republicservices.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gupta.republicservices.databinding.ItemDriverListBinding
import com.gupta.republicservices.models.Driver

class DriverAdapter : ListAdapter<Driver, DriverAdapter.DriverViewHolder>(DriverDiffCallback()) {
    private var listener: ((Driver) -> Unit)? = null

    fun setOnItemClickListener(listener: (Driver) -> Unit) {
        this.listener = listener
    }

    fun sortListByLastName() {
        submitList(currentList.sortedBy { it.lastName })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding =
            ItemDriverListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DriverViewHolder(private val binding: ItemDriverListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.invoke(getItem(adapterPosition))
            }
        }

        fun bind(driver: Driver) {
            binding.tvName.text = driver.name
        }
    }

    class DriverDiffCallback : DiffUtil.ItemCallback<Driver>() {
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
            return oldItem == newItem
        }
    }
}
