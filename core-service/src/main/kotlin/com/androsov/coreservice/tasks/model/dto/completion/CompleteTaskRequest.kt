package com.androsov.coreservice.tasks.model.dto.completion

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class CompleteTaskRequest(
    val completedDate: LocalDate,
    val completedStartTime: LocalTime,
    val completedAt: LocalDateTime,
)