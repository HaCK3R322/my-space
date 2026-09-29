package com.androsov.coreservice.tasks.model.dto

import com.androsov.coreservice.tasks.model.inner.Task
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

data class TaskDto(
    val id: UUID,
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

    // completion
    val completions: List<CompletionDto>
) {
    data class CompletionDto(
        val completedDate: LocalDate,
        val completedStartTime: LocalTime,
        val completedAt: LocalDateTime,
    ) {
        companion object {
            fun from(model: Task.Completion) =
                CompletionDto(
                    completedDate = model.completedDate,
                    completedStartTime = model.completedStartTime,
                    completedAt = model.completedAt,
                )
        }
    }

    companion object {
        fun from(task: Task) =
            TaskDto(
                id = task.id,
                description = task.description,
                daysOfWeekRepeat = task.daysOfWeekRepeat,
                daysOfMonthRepeat = task.daysOfMonthRepeat,
                everyNDaysRepeat = task.everyNDaysRepeat,
                time = task.time,
                startTime = task.startTime,
                durationInMinutes = task.durationInMinutes,
                firstDay = task.firstDay,
                lastDay = task.lastDay,
                completions = task.completions.map { CompletionDto.from(it) }
            )
    }
}
