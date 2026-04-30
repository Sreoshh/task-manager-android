package com.example.taskmanager.mapper

import com.example.taskmanager.data.entity.Task
import com.example.taskmanager.network.dto.TaskDto

// 🔹 Entity → DTO (for API)
fun Task.toDto(): TaskDto {
    return TaskDto(
        id = this.id.toLong(),
        title = this.title ?: "",
        description = this.description ?: "",
        completed = this.isCompleted,
        dueDateTime = this.dueDateTime,
        repeatIntervalDays = this.repeatIntervalDays
    )
}

//  DTO → Entity (for Room DB)
fun TaskDto.toEntity(): Task {
    return Task(
        title ?: "",
        description ?: "",
        dueDateTime,
        dueDateTime > 0,
        repeatIntervalDays
    ).also {
        it.setId(id?.toLong() ?: 0)
        it.setCompleted(completed)
    }
}