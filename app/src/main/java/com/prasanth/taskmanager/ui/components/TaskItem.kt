package com.prasanth.taskmanager.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prasanth.taskmanager.data.local.model.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    onCheckedChange: (TaskEntity) -> Unit,
    onTaskUpdated: (TaskEntity) -> Unit,
    onDelete: (TaskEntity) -> Unit,
    onTaskCompleted: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(task.isCompleted) }
    var taskName by remember { mutableStateOf(task.taskName) }
    var isEditing by remember { mutableStateOf(false) }
    var tempTaskName by remember { mutableStateOf(task.taskName) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { checked ->
                    isChecked = checked
                    if (checked) {
                        onTaskCompleted(task)
                    } else {
                        onCheckedChange(task.copy(isCompleted = false))
                    }
                },
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (isEditing) {
                TextField(
                    value = tempTaskName,
                    onValueChange = { tempTaskName = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            } else {
                Text(
                    text = taskName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (isChecked) Color.Gray else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isEditing) {
                IconButton(onClick = {
                    taskName = tempTaskName
                    onTaskUpdated(task.copy(taskName = taskName))
                    isEditing = false
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint = Color.Green)
                }

                IconButton(onClick = {
                    tempTaskName = taskName
                    isEditing = false
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel", tint = Color.Red)
                }
            } else {
                IconButton(onClick = {
                    tempTaskName = taskName
                    isEditing = true
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                }

                IconButton(onClick = { onDelete(task) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}