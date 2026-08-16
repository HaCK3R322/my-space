package com.androsov.coreservice.tasks.model.dto

import com.androsov.coreservice.tasks.model.Task
import com.androsov.coreservice.tasks.model.entity.TaskEntity
import java.util.UUID

data class TaskDto(
    val id: UUID,
    val description: String
) {
    companion object {
        fun from(task: Task) =
            TaskDto(
                id = task.id,
                description = task.description
            )
    }
}