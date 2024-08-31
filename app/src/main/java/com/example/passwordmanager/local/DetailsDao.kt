package com.example.passwordmanager.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanager.model.AccountData
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewDetails(accountData: AccountData)

    @Update
    suspend fun updateDetails(accountData: AccountData)

    @Delete
    suspend fun deleteDetails(accountData: AccountData)

    @Query("SELECT * FROM account_details")
    fun getAllAccountsDetails(): Flow<List<AccountData>>
}