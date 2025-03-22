package com.prasanth.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.prasanth.taskmanager.data.local.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM  TaskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>


    @Insert
    suspend fun addTasks(tasks: List<TaskEntity>)

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun crashGetTask(id: Int): TaskEntity


}