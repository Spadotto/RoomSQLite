package com.example.roomsqlite.ui.add_edit_category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomsqlite.R
import com.example.roomsqlite.SuperMarketApplication
import com.example.roomsqlite.databinding.FragmentAddEditCategoryBinding
import com.example.roomsqlite.data.Category
import com.example.roomsqlite.data.Item
import com.example.roomsqlite.ui.adapters.ItemAdapter
import com.example.roomsqlite.ui.category.CategoryViewModel
import com.example.roomsqlite.ui.item.ItemViewModel

class AddEditCategoryFragment: Fragment(R.layout.fragment_add_edit_category), ItemAdapter.OnItemClickListener {

    val args: AddEditCategoryFragmentArgs by navArgs()

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModel.CategoryViewModelFactory((activity?.application as SuperMarketApplication).daoCategory)
    }

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory((activity?.application as SuperMarketApplication).daoItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditCategoryBinding.bind(view)

        val newCategory = args.newCategory == null

        val itemAdapter = ItemAdapter(this)

        val category = args.newCategory ?: Category("")

        itemViewModel.item.observe(viewLifecycleOwner) {
            itemViewModel.idCategory.value = category.id
            itemAdapter.submitList(it)
        }

        binding.apply {
            recyclerviewItem.apply {
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(false)
            }
            editCategoryName.setText(category.categoryName)
            textviewDate.isVisible = args.newCategory != null
            textviewDate.text = "Created date: ${category.created}"
            textviewDate.text = "Created date: ${category.createdDateFormat}"

            confirmButton.setOnClickListener {
                if (editCategoryName.text.isEmpty()) {
                    Toast.makeText(activity, "Name of category can't be empty", Toast.LENGTH_SHORT).show()
                } else {
                    if (newCategory) {
                        categoryViewModel.insert(Category(categoryName = editCategoryName.text.toString()))
                    } else {
                        categoryViewModel.update(category.copy(categoryName = editCategoryName.text.toString()))
                    }
                    findNavController().navigateUp()
                }

            }

        }

    }

    override fun onItemClick(item: Item) {
        // pass
    }

    override fun onCheckBoxClick(item: Item, isChecked: Boolean) {
        itemViewModel.onItemCheckedChanged(item, isChecked)
    }

    override fun onDeleteClick(item: Item) {
        itemViewModel.update(item.copy(idCategory = -1))
    }

}