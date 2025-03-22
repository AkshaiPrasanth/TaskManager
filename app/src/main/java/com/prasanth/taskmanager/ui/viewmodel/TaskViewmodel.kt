package com.prasanth.taskmanager.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.prasanth.taskmanager.data.local.model.TaskEntity
import com.prasanth.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val firebaseAnalytics = Firebase.analytics

    val tasks: StateFlow<List<TaskEntity>> =
        repository.getAllTasks()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("is_first_run", true)

        if (isFirstRun) {
            viewModelScope.launch {
                _isLoading.value = true
                repository.fetchAndStoreTasks()
                _isLoading.value = false
                prefs.edit().putBoolean("is_first_run", false).apply()
            }
        }
    }

    fun addTask(taskName: String) {
        viewModelScope.launch {
            val task = TaskEntity(id = 0, taskName = taskName, isCompleted = false)
            repository.addTask(task)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, task.id.toString())
                param(FirebaseAnalytics.Param.ITEM_NAME, task.taskName)
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "task")
            }
            Toast.makeText(context, "Task '${task.taskName}' added", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
            firebaseAnalytics.logEvent("Task_Edited") {
                param(FirebaseAnalytics.Param.ITEM_ID, task.id.toString())
                param(FirebaseAnalytics.Param.ITEM_NAME, task.taskName)
                param("is_completed", task.isCompleted.toString())
            }
            Toast.makeText(context, "Task '${task.taskName}' updated", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
            firebaseAnalytics.logEvent("Task_Deleted") {
                param(FirebaseAnalytics.Param.ITEM_ID, task.id.toString())
                param(FirebaseAnalytics.Param.ITEM_NAME, task.taskName)
            }
            Toast.makeText(context, "Task '${task.taskName}' deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun markTaskCompleted(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = true)
            repository.updateTask(updatedTask)
            firebaseAnalytics.logEvent("Task_Completed") {
                param(FirebaseAnalytics.Param.ITEM_ID, task.id.toString())
                param(FirebaseAnalytics.Param.ITEM_NAME, task.taskName)
            }
            Toast.makeText(context, "Task '${task.taskName}' completed", Toast.LENGTH_SHORT).show()
        }
    }

    fun testCrash() {
        firebaseAnalytics.logEvent("crash_test") {
            param("type", "manual_test")
        }
        throw RuntimeException("Test crash for Firebase Crashlytics")
    }

    fun testDatabaseCrash() {
        firebaseAnalytics.logEvent("database_crash_test") {
            param("type", "room_db")
        }
        try {
            repository.testDatabaseCrash(-1)
        } catch (e: Exception) {
            Toast.makeText(context, "Database crash triggered", Toast.LENGTH_SHORT).show()
            throw e
        }
    }
}