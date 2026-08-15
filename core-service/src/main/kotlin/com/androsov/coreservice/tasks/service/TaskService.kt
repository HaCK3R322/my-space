package com.androsov.coreservice.tasks.service

import com.androsov.coreservice.tasks.model.Task
import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {
    fun createTask(request: TaskCreateRequestDto): Task {
        val task =
            Task(
                id = UUID.randomUUID(),
                description = request.description
            )

        taskRepository.save(task = task)

        return task
    }
}