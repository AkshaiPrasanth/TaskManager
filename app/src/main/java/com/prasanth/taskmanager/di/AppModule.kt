package com.prasanth.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.prasanth.taskmanager.data.local.TaskDao
import com.prasanth.taskmanager.data.local.TaskDatabase
import com.prasanth.taskmanager.data.remote.TaskApiService
import com.prasanth.taskmanager.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideApiService(): TaskApiService {
        return Retrofit.Builder()
            .baseUrl("https://67dc5bfae00db03c4067d276.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
    @Provides
    fun provideTaskRepository(taskDao: TaskDao, apiService: TaskApiService): TaskRepository {
        return TaskRepository(taskDao, apiService)
    }
}

