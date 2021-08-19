package com.example.roomsqlite.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM itens_table")
    suspend fun deleteAll()

    fun getItens(id: Int = -1): Flow<List<Item>> =
        when(id) {
            -1 -> getAllItens()
            else -> getItemFromCategory(id)
        }

    @Query("SELECT * FROM itens_table")
    fun getAllItens(): Flow<List<Item>>

    @Query("SELECT * FROM itens_table WHERE idCategory = :id")
    fun getItemFromCategory(id: Int): Flow<List<Item>>

}