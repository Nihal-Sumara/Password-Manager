package com.example.passwordmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.model.AccountData
import com.example.passwordmanager.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val allData = repository.getAllAccounts()

    suspend fun insertData(accountData: AccountData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(accountData)
        }
    }

    suspend fun deleteData(accountData: AccountData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(accountData)
        }
    }

    suspend fun updateData(accountData: AccountData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(accountData)
        }
    }
}