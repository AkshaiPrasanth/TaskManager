package com.prasanth.taskmanager.data.repository

import com.prasanth.taskmanager.data.local.TaskDao
import com.prasanth.taskmanager.data.local.model.TaskEntity
import com.prasanth.taskmanager.data.remote.TaskApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao,private val apiService: TaskApiService) {

    fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun addTask(task: TaskEntity) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
    suspend fun fetchAndStoreTasks() {
        try {
            val response = apiService.getTasks()
            if (response.isSuccessful) {
                response.body()?.let { tasks ->
                    val entities = tasks.map { task ->
                        TaskEntity(
                            id = 0, // Let Room generate the ID
                            taskName = task.taskName,
                            isCompleted = task.isCompleted
                        )
                    }
                    taskDao.addTasks(entities)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun testDatabaseCrash(id: Int) {
        taskDao.crashGetTask(id)
    }
}

