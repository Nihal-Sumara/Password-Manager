package com.example.passwordmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account_details")
data class AccountData(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val accountName: String?,
    val email: String?,
    val password: String?
) : Parcelable
