package com.androsov.coreservice.tasks.repository

import com.androsov.coreservice.tasks.model.entity.TaskEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : CrudRepository<TaskEntity, UUID>