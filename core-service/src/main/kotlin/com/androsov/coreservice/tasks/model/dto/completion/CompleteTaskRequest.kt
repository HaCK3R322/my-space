package com.androsov.coreservice.tasks.model.dto.completion

import java.time.LocalDateTime

data class CompleteTaskRequest(
    val dateTime: LocalDateTime,
)