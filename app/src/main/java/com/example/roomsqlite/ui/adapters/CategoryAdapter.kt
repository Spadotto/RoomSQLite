package com.example.roomsqlite.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsqlite.databinding.ItemCategoryBinding
import com.example.roomsqlite.data.Category

class CategoryAdapter(private val listener: OnItemClickListener) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(
    CATEGORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val category = getItem(position)
                        listener.onItemClick(category)
                    }
                }

                checkboxCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val category = getItem(position)
                        listener.onCheckBoxClick(category, checkboxCompleted.isChecked)
                    }
                }

                imageviewDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val category = getItem(position)
                        listener.onDeleteClick(category)
                    }
                }
            }
        }

        fun bind(category: Category) {
            binding.apply {
                textviewCategory.text = category.categoryName
                checkboxCompleted.isChecked = category.completed
                textviewCategory.paint.isStrikeThruText = category.completed
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
        fun onCheckBoxClick(category: Category, isChecked: Boolean)
        fun onDeleteClick(category: Category)
    }

    companion object {
        private val CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

        }
    }

}