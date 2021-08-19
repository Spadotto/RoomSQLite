package com.example.roomsqlite.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomsqlite.R
import com.example.roomsqlite.SuperMarketApplication
import com.example.roomsqlite.databinding.FragmentCategoryBinding
import com.example.roomsqlite.ui.adapters.CategoryAdapter
import com.example.roomsqlite.data.Category
import com.example.roomsqlite.ui.item.ItemFragmentDirections
import com.example.roomsqlite.ui.item.ItemViewModel

class CategoryFragment: Fragment(R.layout.fragment_category), CategoryAdapter.OnItemClickListener {

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModel.CategoryViewModelFactory((activity?.application as SuperMarketApplication).daoCategory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCategoryBinding.bind(view)

        val categoryAdapter = CategoryAdapter(this)

        binding.apply {
            recyclerviewCategory.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            addCategory.setOnClickListener {
                val action = CategoryFragmentDirections.actionCategoryFragmentToAddEditCategoryFragment()
                findNavController().navigate(action)
            }
            buttomitem.setOnClickListener {
                val action = CategoryFragmentDirections.actionCategoryFragmentToItemFragment()
                findNavController().navigate(action)
            }
        }

        categoryViewModel.allCategory.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }

    }

    override fun onItemClick(category: Category) {
        val action = CategoryFragmentDirections.actionCategoryFragmentToAddEditCategoryFragment(category)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(category: Category, isChecked: Boolean) {
        categoryViewModel.onCategoryCheckedChanged(category, isChecked)
    }

    override fun onDeleteClick(category: Category) {
        categoryViewModel.delete(category)
    }

}