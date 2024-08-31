package com.example.passwordmanager.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanager.model.AccountData

@Database(entities = [AccountData::class], version = 4)
abstract class AccountsDatabase : RoomDatabase() {
    abstract fun detailsDao(): DetailsDao
}