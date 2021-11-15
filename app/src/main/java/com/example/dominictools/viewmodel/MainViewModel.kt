/*
 * Copyright (c) 2020 Indra Azimi. All rights reserved.
 *
 *  Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 *  Dilarang melakukan penggandaan dan atau komersialisasi,
 *  sebagian atau seluruh bagian, baik cetak maupun elektronik
 *  terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.example.dominictools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dominictools.data.ToolsDao
import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.data.entities.Tool
import com.example.dominictools.data.entities.relations.FriendAndLoan
import com.example.dominictools.data.entities.relations.ToolAndLoans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainViewModel(private val db : ToolsDao) : ViewModel() {

    fun getToolAndLoans(): LiveData<List<ToolAndLoans>> = db.getAllToolAndLoans()
    fun getFriends(): LiveData<List<Friend>> = db.getFriends()
    fun getLoan(friendName: String, toolName: String): Loan? = runBlocking { db.getLoan(friendName, toolName) }
    fun getFriendAndLoan(friendName: String): LiveData<FriendAndLoan> = db.getFriendAndLoanByName(friendName)

    fun insertLoan(loan: Loan){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.insertLoan(loan)
            }
        }
    }

    fun deleteLoan(loan: Loan) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.deleteLoan(loan)
            }
        }
    }

    fun updateLoan(loan: Loan) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.updateLoan(loan)
            }
        }
    }
}
