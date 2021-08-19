package com.example.roomsqlite.ui.item

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomsqlite.R
import com.example.roomsqlite.SuperMarketApplication
import com.example.roomsqlite.databinding.FragmentItemBinding
import com.example.roomsqlite.ui.adapters.ItemAdapter
import com.example.roomsqlite.data.Item

class ItemFragment: Fragment(R.layout.fragment_item), ItemAdapter.OnItemClickListener {

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory((activity?.application as SuperMarketApplication).daoItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentItemBinding.bind(view)

        val itemAdapter = ItemAdapter(this)

        binding.apply {
            recyclerviewItem.apply {
                adapter = itemAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            addItem.setOnClickListener {
                val action = ItemFragmentDirections.actionItemFragmentToAddEditItemFragment()
                findNavController().navigate(action)
            }
            buttomcategory.setOnClickListener {
                val action = ItemFragmentDirections.actionItemFragmentToCategoryFragment()
                findNavController().navigate(action)
            }

        }

        itemViewModel.item.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }

    }

    override fun onItemClick(item: Item) {
        val action = ItemFragmentDirections.actionItemFragmentToAddEditItemFragment(item)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(item: Item, isChecked: Boolean) {
        itemViewModel.onItemCheckedChanged(item, isChecked)
    }

    override fun onDeleteClick(item: Item) {
        itemViewModel.delete(item)
    }

}