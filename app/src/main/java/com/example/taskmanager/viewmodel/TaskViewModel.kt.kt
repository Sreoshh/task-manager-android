package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.database.TaskDatabase
import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.data.repository.TaskRepository
import kotlinx.coroutines.launch

class `TaskViewModel.kt`(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    private val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getInstance(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.getAllTasks()

        // 🔄 sync backend → local on app start
        syncFromBackend()
    }

    fun getAllTasks(): LiveData<List<Task>> = allTasks

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    private fun syncFromBackend() = viewModelScope.launch {
        try {
            repository.syncFromBackend()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
