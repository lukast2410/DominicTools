package com.example.dominictools.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Friend(
    @PrimaryKey(autoGenerate = false)
    val name: String
)
