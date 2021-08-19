package com.example.roomsqlite.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query ("DELETE FROM categories_table")
    suspend fun deleteAll()

    @Query ("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    @Query ("SELECT categoryName FROM categories_table")
    fun getCategoriesNames(): Flow<List<String>>

}