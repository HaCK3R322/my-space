package com.androsov.coreservice.tasks.model.dto

import com.androsov.coreservice.tasks.model.Task
import java.util.UUID

data class TaskCreateResponseDto(
    val data: TaskDto
) {
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

    companion object {
        fun from(task: Task) =
            TaskCreateResponseDto(
                data = TaskDto.from(task)
            )
    }
}
