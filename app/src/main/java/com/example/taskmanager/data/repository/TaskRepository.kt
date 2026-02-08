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

    // 🔹 LOCAL (Room)
    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task)

            // send to backend
            RetrofitClient.taskApi.createTask(task.toDto())
        }
    }

    suspend fun delete(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.delete(task)

            // delete from backend
            task.id?.let {
                RetrofitClient.taskApi.deleteTask(it)
            }
        }
    }

    suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.update(task)

            task.id?.let {
                RetrofitClient.taskApi.updateTask(it, task.toDto())
            }
        }
    }

    // 🔄 SYNC: backend → local
    suspend fun syncFromBackend() {
        withContext(Dispatchers.IO) {
            val remoteTasks = RetrofitClient.taskApi.getAllTasks()
            taskDao.deleteAll()
            taskDao.insertAll(remoteTasks.map { it.toEntity() })
        }
    }
}
