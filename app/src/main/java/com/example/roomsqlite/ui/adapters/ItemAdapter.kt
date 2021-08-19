package com.example.roomsqlite.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsqlite.databinding.ItemItemBinding
import com.example.roomsqlite.data.Item

class ItemAdapter(private val listener: OnItemClickListener) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(
    ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ItemViewHolder(private val binding: ItemItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onItemClick(item)
                    }
                }

                checkboxAdded.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onCheckBoxClick(item, checkboxAdded.isChecked)
                    }
                }

                imageviewDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onDeleteClick(item)
                    }
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                textviewItem.text = item.itemName
                checkboxAdded.isChecked = item.completed
                textviewItem.paint.isStrikeThruText = item.completed
                imageviewImportant.isVisible = item.important
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
        fun onCheckBoxClick(item: Item, isChecked: Boolean)
        fun onDeleteClick(item: Item)
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }

}