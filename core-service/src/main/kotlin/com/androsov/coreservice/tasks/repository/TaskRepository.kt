package com.androsov.coreservice.tasks.repository

import com.androsov.coreservice.tasks.model.Task
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<Task, Long> {
    fun save(task: Task)
}