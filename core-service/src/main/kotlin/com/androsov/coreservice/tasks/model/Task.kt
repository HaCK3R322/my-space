package com.androsov.coreservice.tasks.model

import com.androsov.coreservice.tasks.model.entity.TaskEntity
import java.util.UUID

data class Task(
    val id: UUID,
    val description: String
) {
    companion object {
        fun fromEntity(entity: TaskEntity) = Task(
            id = entity.id ?: error("Cannot create task from entity with null id"),
            description = entity.description
        )
    }
}