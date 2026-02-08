package com.example.taskmanager.network.dto

data class TaskDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    val completed: Boolean,
    val dueDateTime: Long,
    val repeatIntervalDays: Int
)
