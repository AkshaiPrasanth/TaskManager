package com.prasanth.taskmanager.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prasanth.taskmanager.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel, modifier: Modifier = Modifier) {
    val tasks by viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newTaskName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Manager") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFADD8E6)
                ),
                actions = {
                    IconButton(onClick = { viewModel.testCrash() }) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Test Crash",
                            tint = Color.Yellow
                        )
                    }
                    IconButton(onClick = { viewModel.testDatabaseCrash() }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Test Database Crash",
                            tint = Color.Red
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFFADD8E6)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(items = tasks,
                        key = { task -> task.id }
                    ) { task ->

                        TaskItem(
                            task = task,
                            onCheckedChange = { updatedTask -> viewModel.updateTask(updatedTask) },
                            onTaskUpdated = { updatedTask -> viewModel.updateTask(updatedTask) },
                            onDelete = { viewModel.deleteTask(task) },
                            onTaskCompleted = { completedTask -> viewModel.markTaskCompleted(completedTask) } // New callback
                        )
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Add Task") },
                    text = {
                        TextField(
                            value = newTaskName,
                            onValueChange = { newTaskName = it },
                            placeholder = { Text("Task Name") }
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newTaskName.isNotBlank()) {
                                    viewModel.addTask(newTaskName.trim())
                                    newTaskName = ""
                                    showDialog = false
                                }
                            }
                        ) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}