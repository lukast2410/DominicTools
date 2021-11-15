package com.example.dominictools.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tool(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val count: Int
)
