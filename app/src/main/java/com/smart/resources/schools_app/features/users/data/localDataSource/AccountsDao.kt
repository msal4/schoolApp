package com.smart.resources.schools_app.features.users.data.localDataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.resources.schools_app.features.users.data.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {
    @Query("SELECT * FROM Account")
    fun getAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM Account WHERE userId = :userId")
    suspend fun getAccountByUserId(userId: String): Account

    @Query("SELECT * FROM Account INNER JOIN User ON Account.userId == User.userId WHERE backendUserId = :backendUserId")
    suspend fun getAccountByBackendUserId(backendUserId: String): List<Account>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(Account: Account)

    @Query("UPDATE Account SET img = :newImg WHERE userId = :id")
    suspend fun updateImg(id:String,newImg:String)

    @Query("DELETE FROM Account WHERE userId=:id")
    suspend fun deleteAccountById(id: String)

}