package com.example.roomsqlite

import android.app.Application
import com.example.roomsqlite.data.CategoryDao
import com.example.roomsqlite.data.DatabaseRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SuperMarketApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { DatabaseRoom.getDatabase(this, applicationScope) }
    val daoCategory by lazy { (database.categoryDao()) }
    val daoItem by lazy { (database.itemDao()) }

}