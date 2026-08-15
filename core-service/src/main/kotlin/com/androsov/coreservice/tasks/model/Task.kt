package com.androsov.coreservice.tasks.model

import com.androsov.coreservice.tasks.model.dto.TaskCreateResponseDto
import java.util.UUID

data class Task(
    val id: UUID,
    val description: String
)
