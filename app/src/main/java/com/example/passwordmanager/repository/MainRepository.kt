package com.example.passwordmanager.repository

import com.example.passwordmanager.local.AccountsDatabase
import com.example.passwordmanager.model.AccountData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val accountsDatabase: AccountsDatabase) {

    fun getAllAccounts(): Flow<List<AccountData>> =
        accountsDatabase.detailsDao().getAllAccountsDetails()

    suspend fun insertData(accountData: AccountData) =
        accountsDatabase.detailsDao().insertNewDetails(accountData)

    suspend fun updateData(accountData: AccountData) =
        accountsDatabase.detailsDao().updateDetails(accountData)

    suspend fun deleteData(accountData: AccountData) =
        accountsDatabase.detailsDao().deleteDetails(accountData)
}