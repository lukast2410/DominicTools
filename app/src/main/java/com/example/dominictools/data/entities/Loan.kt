package com.example.dominictools.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Loan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val friendName: String,
    val toolName: String,
    var count: Int
)
