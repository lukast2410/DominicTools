package com.example.dominictools.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dominictools.data.entities.Loan
import com.example.dominictools.data.entities.Tool

data class ToolAndLoans(
    @Embedded val tool: Tool,
    @Relation(
        parentColumn = "name",
        entityColumn = "toolName"
    )
    val loans: List<Loan>
)
