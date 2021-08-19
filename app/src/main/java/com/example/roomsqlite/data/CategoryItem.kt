package com.example.roomsqlite.data

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryItem (
    @Embedded val category: Category,
    @Relation (
        parentColumn = "id",
        entityColumn = "idCategory"
    )
    val item: List<Item>
)
