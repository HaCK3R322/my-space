package com.androsov.coreservice.tasks.controller

import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.model.dto.TaskCreateResponseDto
import com.androsov.coreservice.tasks.model.dto.TaskDto
import com.androsov.coreservice.tasks.service.TaskService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

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

    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskDto> {
        return taskService.getAllTasks().map { TaskDto.from(it) }
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable id: UUID): TaskDto = TaskDto.from(taskService.getTaskById(id))

    @DeleteMapping("/tasks")
    fun deleteAllTasks() = taskService.deleteAllTasks()
}