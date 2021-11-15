package com.example.dominictools.data.entities.relations

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation
import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.Loan

data class FriendAndLoan(
    @Embedded val friend: Friend,
    @Relation(
        parentColumn = "name",
        entityColumn = "friendName",
        entity = Loan::class
    )
    val loans: List<Loan>
)
