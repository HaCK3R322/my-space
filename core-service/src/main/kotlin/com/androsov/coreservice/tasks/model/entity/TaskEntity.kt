package com.androsov.coreservice.tasks.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id val id: UUID,
    // Полезная информация
    val description: String,
    // Шаблон-параметры для описания времени выполнения задачи
    val daysOfWeekRepeat: Set<DayOfWeek>? = null,
    val daysOfMonthRepeat: Set<Int>? = null,
    val everyNDaysRepeat: Int? = null,
    val time: Time? = null,
    val startTime: Time? = null,
    val durationInMinutes: Long? = null,
    // Диапазон дат, среди которых существует задача
    val firstDay: LocalDate? = null,
    val lastDay: LocalDate? = null,
)
