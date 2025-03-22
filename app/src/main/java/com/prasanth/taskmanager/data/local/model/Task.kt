package com.prasanth.taskmanager.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskName: String,
    val isCompleted: Boolean
)