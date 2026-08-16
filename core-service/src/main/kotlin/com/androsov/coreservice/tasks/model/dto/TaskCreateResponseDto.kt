package com.androsov.coreservice.tasks.model.dto

import com.androsov.coreservice.tasks.model.Task

data class TaskCreateResponseDto(
    val data: TaskDto
) {

    companion object {
        fun from(task: Task) = TaskCreateResponseDto(
            data = TaskDto.from(task)
        )
    }
}

