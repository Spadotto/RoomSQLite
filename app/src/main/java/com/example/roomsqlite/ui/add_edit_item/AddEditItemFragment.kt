package com.example.roomsqlite.ui.add_edit_item

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomsqlite.R
import com.example.roomsqlite.SuperMarketApplication
import com.example.roomsqlite.data.Category
import com.example.roomsqlite.data.Item
import com.example.roomsqlite.databinding.FragmentAddEditItemBinding
import com.example.roomsqlite.ui.category.CategoryViewModel
import com.example.roomsqlite.ui.item.ItemViewModel

class AddEditItemFragment: Fragment(R.layout.fragment_add_edit_item),
    AdapterView.OnItemSelectedListener {

    var namecategory: String = ""
    var idcategory: Int = 0
    val args: AddEditItemFragmentArgs by navArgs()

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory((activity?.application as SuperMarketApplication).daoItem)
    }

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModel.CategoryViewModelFactory((activity?.application as SuperMarketApplication).daoCategory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditItemBinding.bind(view)

        val spinner: Spinner = binding.listCategory

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        adapter.add("Select category")
        categoryViewModel.allNames.observe(viewLifecycleOwner) {
            adapter.addAll(it)
        }
        spinner.adapter = adapter

        val newitem = args.newItem == null

        val item = args.newItem ?: Item("", "", false, false, -1)

        binding.apply {
            editItemName.setText(item.itemName)
            edittextQuantity.setText(item.quantity)
            checkboxImportant.isChecked = item.important
            checkboxImportant.jumpDrawablesToCurrentState()
            spinner.onItemSelectedListener = this@AddEditItemFragment

            confirmButton.setOnClickListener {
                if (idcategory != -1) {
                    if (editItemName.text.isEmpty() || edittextQuantity.text.isEmpty()) {
                        Toast.makeText(activity, "Item and quantity can't be empty.", Toast.LENGTH_SHORT).show()
                    } else {
                        if (newitem) {
                            itemViewModel.insert(Item(itemName = editItemName.text.toString(), quantity = edittextQuantity.text.toString(), important = checkboxImportant.isChecked, idCategory = idcategory))
                        } else {
                            itemViewModel.update(item.copy(itemName = editItemName.text.toString(), quantity = edittextQuantity.text.toString(), important = checkboxImportant.isChecked, idCategory = idcategory))
                        }
                        findNavController().navigateUp()
                    }
                } else {
                    onNothingSelected(spinner)
                }

            }

        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        namecategory = parent?.getItemAtPosition(position).toString()

        categoryViewModel.allCategory.observe(viewLifecycleOwner) {
            var index = 0
            for (i in it) {
                if (it[index].categoryName == namecategory) {
                    idcategory = it[index].id
                    break
                } else if (namecategory == "Select category") {
                    idcategory = -1
                }
                index += 1
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(activity, "Please select a category above!", Toast.LENGTH_SHORT).show()
    }

}
