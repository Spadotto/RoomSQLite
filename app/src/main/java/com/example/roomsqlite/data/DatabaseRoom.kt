package com.example.roomsqlite.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Category::class, Item::class], version = 1)
abstract class DatabaseRoom: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getDatabase (
            context: Context,
            scope: CoroutineScope
        ): DatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "supermarket.database"
                ).fallbackToDestructiveMigration()
                    .addCallback(DatabaseRoomCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    private class DatabaseRoomCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.categoryDao())
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao) {
            categoryDao.deleteAll()
            categoryDao.insert(Category("Category Test"))
            categoryDao.insert(Category("Vegetables"))
        }

    }

}