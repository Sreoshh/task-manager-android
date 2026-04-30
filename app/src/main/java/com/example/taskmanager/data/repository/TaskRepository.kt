package com.example.taskmanager.data.repository

import com.example.taskmanager.data.dao.TaskDao
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.mapper.toDto
import com.example.taskmanager.mapper.toEntity
import com.example.taskmanager.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val taskDao: TaskDao
) {

    //  LOCAL (Room)
    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task)

            try {
                RetrofitClient.taskApi.createTask(task.toDto())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun delete(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.delete(task)

            try {
                task.id.let {
                    RetrofitClient.taskApi.deleteTask(it.toLong())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.update(task)

            try {
                task.id.let {
                    RetrofitClient.taskApi.updateTask(it.toLong(), task.toDto())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun syncFromBackend() {
        withContext(Dispatchers.IO) {
            try {
                val remoteTasks = RetrofitClient.taskApi.getAllTasks()

                if (remoteTasks.isNotEmpty()) {
                    taskDao.deleteAll()
                    taskDao.insertAll(remoteTasks.map { it.toEntity() })
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
