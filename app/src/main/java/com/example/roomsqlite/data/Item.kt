package com.example.roomsqlite.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "itens_table")
@Parcelize
data class Item(
    val itemName: String,
    val quantity: String,
    val completed: Boolean = false,
    val important: Boolean = false,
    val idCategory: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
    ) : Parcelable