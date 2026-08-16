package com.androsov.coreservice.tasks.service

import com.androsov.coreservice.tasks.model.Task
import com.androsov.coreservice.tasks.model.entity.TaskEntity
import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.model.dto.TaskDto
import com.androsov.coreservice.tasks.repository.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {
    private val logger = LoggerFactory.getLogger(TaskService::class.java)

    fun createTask(request: TaskCreateRequestDto): Task {
        val savedTaskEntity = taskRepository.save(
            TaskEntity(
                description = request.description
            )
        )

        logger.info("Saved task: {$savedTaskEntity}")

        return Task.fromEntity(savedTaskEntity)
    }

    fun getAllTasks(): List<Task> =
        taskRepository
            .findAll()
            .toList()
            .map { Task.fromEntity(it) }

    fun getTaskById(id: UUID): Task {
        val taskEntity: TaskEntity =
            taskRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Task not found with id: $id")

        return Task.fromEntity(taskEntity)
    }

    fun deleteAllTasks() {
        taskRepository.deleteAll()
    }
}