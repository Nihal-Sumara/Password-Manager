package com.example.passwordmanager.di

import android.content.Context
import androidx.room.Room
import com.example.passwordmanager.local.AccountsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AccountsDatabase {
        return Room.databaseBuilder(
            context,
            AccountsDatabase::class.java,
            "accountsDB.db"
        ).build()
    }
}