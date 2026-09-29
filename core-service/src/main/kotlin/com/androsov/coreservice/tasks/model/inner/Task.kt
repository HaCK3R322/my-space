package com.androsov.coreservice.tasks.model.inner

import com.androsov.coreservice.tasks.model.entity.TaskCompletionEntity
import com.androsov.coreservice.tasks.model.entity.TaskEntity
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

data class Task(
    val id: UUID,

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

    // completion
    val completions: List<Completion>
) {
    data class Completion(
        val completedDate: LocalDate,
        val completedStartTime: LocalTime,
        val completedAt: LocalDateTime,
    ) {
        companion object {
            fun from(entity: TaskCompletionEntity) =
                Completion(
                    completedDate = entity.completedDate,
                    completedStartTime = entity.completedStartTime,
                    completedAt = entity.completedAt,
                )
        }
    }

    companion object {
        fun from(
            entity: TaskEntity,
            taskCompletionEntities: List<TaskCompletionEntity>,
        ): Task {
            val completions =
                taskCompletionEntities
                    .filter { it.taskId == entity.id }
                    .map { Completion.from(it) }

            return Task(
                id = entity.id ?: error("Task has null id"),
                description = entity.description,
                daysOfWeekRepeat = entity.daysOfWeekRepeat,
                daysOfMonthRepeat = entity.daysOfMonthRepeat,
                everyNDaysRepeat = entity.everyNDaysRepeat,
                time = entity.time,
                startTime = entity.startTime,
                durationInMinutes = entity.durationInMinutes,
                firstDay = entity.firstDay,
                lastDay = entity.lastDay,
                completions = completions
            )
        }
    }
}