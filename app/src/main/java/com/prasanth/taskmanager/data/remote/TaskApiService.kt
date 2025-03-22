package com.prasanth.taskmanager.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface TaskApiService {
    @GET("/api/todos/todol_lists")
    suspend fun getTasks(): Response<List<TaskResponse>>
}

data class TaskResponse(
    val id: Int,
    val taskName: String,
    val isCompleted: Boolean
)