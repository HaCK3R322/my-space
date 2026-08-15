package com.androsov.coreservice.tasks.controller

import com.androsov.coreservice.tasks.model.Task
import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.model.dto.TaskCreateResponseDto
import com.androsov.coreservice.tasks.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TasksController(
    private val taskService: TaskService
) {
    @PostMapping("/tasks")
    fun addTask(@RequestBody taskCreateRequestDto: TaskCreateRequestDto): TaskCreateResponseDto {
        val createdTask =
            taskService.createTask(
                request = taskCreateRequestDto
            )

        val response = TaskCreateResponseDto.from(createdTask)

        return response
    }
}