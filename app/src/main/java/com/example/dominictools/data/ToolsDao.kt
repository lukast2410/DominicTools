package com.example.dominictools.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.data.entities.Tool
import com.example.dominictools.data.entities.relations.FriendAndLoan
import com.example.dominictools.data.entities.relations.ToolAndLoans

@Dao
interface ToolsDao {
    @Insert
    suspend fun insertTools(tools: List<Tool>)

    @Insert
    suspend fun insertFriends(friends: List<Friend>)

    @Insert
    suspend fun insertLoan(loan: Loan)

    @Transaction
    @Query("SELECT * FROM friend")
    fun getFriends(): LiveData<List<Friend>>

    @Transaction
    @Query("SELECT * FROM tool")
    fun getAllToolAndLoans(): LiveData<List<ToolAndLoans>>

    @Transaction
    @Query("SELECT * FROM friend WHERE name = :friendName LIMIT 1")
    fun getFriendAndLoanByName(friendName: String): LiveData<FriendAndLoan>

    @Transaction
    @Query("SELECT * FROM loan WHERE friendName = :friendName AND toolName = :toolName")
    suspend fun getLoan(friendName: String, toolName: String): Loan?

    @Delete
    suspend fun deleteLoan(loan: Loan)

    @Update
    suspend fun updateLoan(loan: Loan)

}