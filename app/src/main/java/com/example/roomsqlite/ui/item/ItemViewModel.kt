package com.example.roomsqlite.ui.item

import androidx.lifecycle.*
import com.example.roomsqlite.data.Item
import com.example.roomsqlite.data.ItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao): ViewModel() {

    val idCategory = MutableStateFlow(-1)
    val itemFlow = idCategory.flatMapLatest {
        itemDao.getItens(it)
    }

    val item = itemFlow.asLiveData()

    fun insert(item: Item) = viewModelScope.launch {
        itemDao.insert(item)
    }

    fun update(item: Item) = viewModelScope.launch {
        itemDao.update(item)
    }

    fun delete(item: Item) = viewModelScope.launch {
        itemDao.delete(item)
    }

    fun deleteAll() = viewModelScope.launch {
        itemDao.deleteAll()
    }

    fun onItemCheckedChanged(item: Item, isChecked: Boolean) = viewModelScope.launch {
        itemDao.update(item.copy(completed = isChecked))
    }

    class ItemViewModelFactory(private val itemDao: ItemDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemViewModel(itemDao) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }

    }

}