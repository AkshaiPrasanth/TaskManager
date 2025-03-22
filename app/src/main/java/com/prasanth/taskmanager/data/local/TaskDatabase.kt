package com.prasanth.taskmanager.data.local


import androidx.room.Database

import androidx.room.RoomDatabase
import com.prasanth.taskmanager.data.local.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1,exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

//    companion object {
//        @Volatile
//        private var INSTANCE: TaskDatabase? = null
//
//        fun getDatabase(context: Context): TaskDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    TaskDatabase::class.java,
//                    "task_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}
