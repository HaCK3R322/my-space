package com.androsov.coreservice.tasks.service

import com.androsov.coreservice.tasks.model.inner.Task
import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.model.entity.TaskCompletionEntity
import com.androsov.coreservice.tasks.model.entity.TaskEntity
import com.androsov.coreservice.tasks.repository.TaskCompletionRepository
import com.androsov.coreservice.tasks.repository.TaskRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val taskCompletionRepository: TaskCompletionRepository,
) {
    fun createTask(request: TaskCreateRequestDto): Task {
        request.validate()

        val taskEntity = TaskEntity.from(request)

        val savedTask = taskRepository.save(taskEntity)

        return Task.from(
            entity = savedTask,
            taskCompletionEntities = listOf()
        )
    }

    fun getAllTasks(): List<Task> {
        val tasksEntities: List<TaskEntity> = taskRepository.findAll().toList()

        val tasksCompletionsEntities: List<TaskCompletionEntity> = taskCompletionRepository.findAll().toList()

        return tasksEntities.map { taskEntity ->
            Task.from(
                entity = taskEntity,
                taskCompletionEntities = tasksCompletionsEntities.filter { it.taskId == taskEntity.id }
            )
        }
    }

    fun getTaskById(id: UUID): Task {
        val taskEntity: TaskEntity =
            taskRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Task not found with id: $id")

        val taskCompletionsEntities: List<TaskCompletionEntity> =
            taskCompletionRepository.findAllByTaskId(taskEntity.id)

        return Task.from(
            entity = taskEntity,
            taskCompletionEntities = taskCompletionsEntities
        )
    }

    fun deleteAllTasks() {
        taskRepository.deleteAll()
    }

    fun completeTask(taskId: UUID, dateTime: LocalDateTime) {
        if (!taskRepository.existsById(taskId)) throw IllegalArgumentException("Task not found with id: $taskId")

        taskCompletionRepository.save(TaskCompletionEntity(
            taskId = taskId,
            completedAt = dateTime
        ))
    }
}