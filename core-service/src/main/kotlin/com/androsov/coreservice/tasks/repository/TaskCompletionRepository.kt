package com.androsov.coreservice.tasks.repository

import com.androsov.coreservice.tasks.model.entity.TaskCompletionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskCompletionRepository : CrudRepository<TaskCompletionEntity, UUID> {
    fun findAllByTaskId(id: UUID?): List<TaskCompletionEntity>
}