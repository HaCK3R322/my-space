package com.androsov.coreservice.tasks.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "tasks_completions")
data class TaskCompletionEntity(
    @Id val id: UUID,
    val taskId: UUID,
    val completedDate: LocalDate,
    val completedStartTime: LocalTime,
    val completedAt: LocalDateTime,
)
