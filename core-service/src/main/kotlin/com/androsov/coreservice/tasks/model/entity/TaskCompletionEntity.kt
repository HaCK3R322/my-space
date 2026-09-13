package com.androsov.coreservice.tasks.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

@Entity
@Table(name = "tasks_completions")
data class TaskCompletionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val taskId: UUID,
    val completedDayOfWeek: DayOfWeek,
    val completedStartTime: LocalTime,

    val completedAt: LocalDateTime
)