package com.androsov.coreservice.tasks.service

import com.androsov.coreservice.core.util.UUIDGenerator
import com.androsov.coreservice.tasks.model.dto.TaskCreateRequestDto
import com.androsov.coreservice.tasks.model.dto.completion.CompleteTaskRequest
import com.androsov.coreservice.tasks.model.entity.TaskCompletionEntity
import com.androsov.coreservice.tasks.model.entity.TaskEntity
import com.androsov.coreservice.tasks.model.inner.Task
import com.androsov.coreservice.tasks.repository.TaskCompletionRepository
import com.androsov.coreservice.tasks.repository.TaskRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(
    private val uuidGenerator: UUIDGenerator,
    private val taskRepository: TaskRepository,
    private val taskCompletionRepository: TaskCompletionRepository,
) {
    fun createTask(request: TaskCreateRequestDto): Task {
        request.validate()

        val taskEntity =
            TaskEntity(
                id = uuidGenerator.generate(),
                description = request.description,
                daysOfWeekRepeat = request.daysOfWeekRepeat,
                daysOfMonthRepeat = request.daysOfMonthRepeat,
                everyNDaysRepeat = request.everyNDaysRepeat,
                time = request.time,
                startTime = request.startTime,
                durationInMinutes = request.durationInMinutes,
                firstDay = request.firstDay,
                lastDay = request.lastDay,
            )

        val savedTask = taskRepository.save(taskEntity)

        return Task.from(
            entity = savedTask,
            taskCompletionEntities = listOf(),
        )
    }

    fun getAllTasks(): List<Task> {
        val tasksEntities: List<TaskEntity> = taskRepository.findAll().toList()

        val tasksCompletionsEntities: List<TaskCompletionEntity> = taskCompletionRepository.findAll().toList()

        return tasksEntities.map { taskEntity ->
            Task.from(
                entity = taskEntity,
                taskCompletionEntities = tasksCompletionsEntities.filter { it.taskId == taskEntity.id },
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
            taskCompletionEntities = taskCompletionsEntities,
        )
    }

    fun deleteAllTasks() {
        taskRepository.deleteAll()
    }

    fun completeTask(
        taskId: UUID,
        request: CompleteTaskRequest,
    ) {
        if (!taskRepository.existsById(taskId)) throw IllegalArgumentException("Task not found with id: $taskId")

        taskCompletionRepository.save(
            TaskCompletionEntity(
                id = uuidGenerator.generate(),
                taskId = taskId,
                completedDate = request.completedDate,
                completedStartTime = request.completedStartTime,
                completedAt = request.completedAt,
            ),
        )
    }
}
