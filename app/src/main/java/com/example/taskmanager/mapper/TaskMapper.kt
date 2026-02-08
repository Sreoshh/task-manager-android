package com.example.taskmanager.mapper

import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.network.dto.TaskDto

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = this.id,
        title = this.title,
        description = this.description,
        completed = this.isCompleted,
        dueDateTime = this.dueDateTime,
        repeatIntervalDays = this.repeatIntervalDays
    )
}

fun TaskDto.toEntity(): Task {
    return Task(
        title = title,
        description = description,
        dueDateTime = dueDateTime,
        reminderEnabled = dueDateTime > 0,
        repeatIntervalDays = repeatIntervalDays
    ).also {
        it.id = id ?: 0
        it.isCompleted = completed
    }
}
