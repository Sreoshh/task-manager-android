package com.example.taskmanager.network.api

import com.example.taskmanager.network.dto.TaskDto
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {

    @GET("/api/tasks")
    suspend fun getAllTasks(): List<TaskDto>

    @POST("/api/tasks")
    suspend fun createTask(@Body task: TaskDto): TaskDto

    @PUT("/api/tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Long,
        @Body task: TaskDto
    ): TaskDto

    @DELETE("/api/tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Long): Response<Unit>
}
