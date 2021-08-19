package com.example.roomsqlite.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "categories_table")
@Parcelize
data class Category (
    val categoryName: String,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
    ): Parcelable {

    val createdDateFormat: String
        get() = DateFormat.getDateTimeInstance().format(created)

}