package com.example.roomsqlite.ui.category

import androidx.lifecycle.*
import com.example.roomsqlite.data.Category
import com.example.roomsqlite.data.CategoryDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CategoryViewModel(private val categoryDao: CategoryDao): ViewModel() {

    val allCategory: LiveData<List<Category>> = categoryDao.getCategories().asLiveData()
    val allNames: LiveData<List<String>> = categoryDao.getCategoriesNames().asLiveData()

    fun insert(category: Category) = viewModelScope.launch {
        categoryDao.insert(category)
    }

    fun update(category: Category) = viewModelScope.launch {
        categoryDao.update(category)
    }

    fun delete(category: Category) = viewModelScope.launch {
        categoryDao.delete(category)
    }

    fun deleteAll() = viewModelScope.launch {
        categoryDao.deleteAll()
    }

    fun onCategoryCheckedChanged(category: Category, isChecked: Boolean) = viewModelScope.launch {
        categoryDao.update(category.copy(completed = isChecked))
    }

    class CategoryViewModelFactory(private val categoryDao: CategoryDao): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(categoryDao) as T
            }
            throw IllegalArgumentException("Unknow ViewModel class")
        }

    }

}